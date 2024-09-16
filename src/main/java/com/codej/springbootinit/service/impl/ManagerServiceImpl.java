package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.ManagerMapper;
import com.codej.springbootinit.mapper.RoleMapper;
import com.codej.springbootinit.mapper.RoleRuleMapper;
import com.codej.springbootinit.mapper.RuleMapper;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.enums.Menu;
import com.codej.springbootinit.model.vo.ManagerPageVo;
import com.codej.springbootinit.model.vo.ManagerVO;
import com.codej.springbootinit.model.vo.UserPermissionsResponse;
import com.codej.springbootinit.service.ManagerService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.codej.springbootinit.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 10306
* @description 针对表【manager(管理员表)】的数据库操作Service实现
* @createDate 2024-09-15 13:39:44
*/
@Slf4j
@Api("管理员接口")
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager>
    implements ManagerService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "codeJ";
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRuleMapper roleRuleMapper;
    @Autowired
    private RuleMapper ruleMapper;
    /**
     * 根据 token 获取用户权限信息
     * @param token
     * @return
     */
    public UserPermissionsResponse getUserPermissionsByToken(String token) {
        // 1. 提取用户名
        String username = JwtUtil.extractUsername(token);

        // 2. 根据用户名查询用户信息
        Manager manager = managerMapper.selectByUsername(username);
        if (manager == null) {
            throw new RuntimeException("用户不存在");
        }

        // 3. 查询角色信息
        Role role = roleMapper.selectRoleById(manager.getRoleId());
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }

        // 4. 查询菜单信息
        List<Menu> menus = roleRuleMapper.selectMenusByRoleId(manager.getRoleId());

        // 5. 查询权限规则
        List<String> ruleNames = ruleMapper.selectRuleNamesByRoleId(manager.getRoleId());

        // 6. 构建返回对象
        UserPermissionsResponse response = new UserPermissionsResponse();
        response.setId(manager.getId());
        response.setUsername(manager.getUsername());
        response.setAvatar(manager.getAvatar());
        response.setIsSuperAdmin(manager.getIsSuperAdmin());
        response.setRole(role);
        response.setMenus(menus);
        response.setRuleNames(ruleNames);

        return response;
    }

    /**
     * 管理员分页查询  todo keyword有问题
     * @param page
     * @param limit
     * @param keyword
     * @return
     */
//    @Override
//    public ManagerPageResponse getManagers(Integer page, Integer limit, String keyword) {
//        // 设置分页
//        Page<Manager> managerPage = new Page<>(page, limit);
//
//        // 查询条件
//        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            queryWrapper.lambda().like(Manager::getUsername, keyword);
//        }
//
//        // 查询管理员数据
//        Page<Manager> resultPage = managerMapper.selectPage(managerPage, queryWrapper);
//
//        // 查询角色信息
//        List<Role> roles = roleMapper.selectAllRoles();  // 使用新方法
//
//        // 构建响应数据
//        ManagerPageResponse response = new ManagerPageResponse();
//        response.setList(resultPage.getRecords());
//        response.setTotalCount(resultPage.getTotal());
//        response.setRoles(roles);
//
//        return response;
//    }
    @Override
    public ManagerPageVo getManagers(int page, int limit, String keyword) {
        // 调用MyBatis-Plus进行分页查询
        // 示例代码，具体实现需要根据实际数据库和业务逻辑调整
        Page<Manager> managerPage = new Page<>(page, limit);
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(Manager::getUsername, keyword);
        }
        IPage<Manager> result = managerMapper.selectPage(managerPage, queryWrapper);
        // 查询角色信息
        List<Role> roles = roleMapper.selectAllRoles();  // 使用新方法
        // 返回分页数据
        return new ManagerPageVo(result.getRecords(), result.getTotal(),roles);
    }


    /**
     * 根据用户名查询管理员信息
     * @param username
     * @return
     */
    @Override
    public Manager getByName(String username) {
        return managerMapper.selectByUsername(username);
    }

    /**
     * 添加管理员
     * @param username
     * @param password
     * @param roleId
     * @param status
     * @param avatar
     * @param token
     * @return
     */
    @Override
    public Manager addManager(String username, String password, Integer roleId, Integer status, String avatar, String token) {
        // 检查用户名是否已存在
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (managerMapper.selectOne(queryWrapper) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户已存在");
        }

        // 创建新用户
        Manager manager = new Manager();
        manager.setUsername(username);
        manager.setPassword(password);
        manager.setRoleId(roleId);
        manager.setStatus(status);
        manager.setAvatar(avatar);
        manager.setCreateTime(System.currentTimeMillis());  // 设置当前时间戳
        manager.setUpdateTime(manager.getCreateTime());      // 设置更新时间戳

        // 插入数据库
        managerMapper.insert(manager);
        return manager;
    }

    @Override
    public void updatePassword(String username, String oldpassword, String newPassword) {
        // 参数校验, 不允许为空
        if (StringUtils.isAnyBlank(username, oldpassword, newPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 新密码和旧密码不能相同
        if (oldpassword.equals(newPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        根据用户名查询管理员密码
        Manager manager = managerMapper.selectByUsername(username);
        String password = manager.getPassword();
        // 2. 对旧密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + oldpassword).getBytes());
//        比对加密后的密码
        if (!password.equals(encryptPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        newPassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());
        manager.setPassword(newPassword);
        managerMapper.updateById(manager);
    }

    @Override
    public Boolean updateManager(Integer id,String username, String password, Integer roleId, Integer status, String avatar, String token) {
        // 检查用户名是否已存在
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Manager existingManager = managerMapper.selectOne(queryWrapper);
        if (existingManager != null && !existingManager.getId().equals(id)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "username已存在");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());// 加密处理

        // 更新管理员信息
        Manager manager = new Manager();
        manager.setId(id);
        manager.setUsername(username);
        manager.setPassword(encryptPassword);
        manager.setRoleId(roleId);
        manager.setStatus(status);
        manager.setAvatar(avatar);
        int i = managerMapper.updateById(manager);
        if (i == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return true;
    }

    /**
     * 删除管理员
     * @param id
     * @param token
     * @return
     */
    @Override
    public boolean deleteManager(Integer id, String token) {
//        判空
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return managerMapper.deleteById(id) > 0;
    }

    /**
     * 修改管理员状态
     * @param id
     * @param token
     * @return
     */
    @Override
    public boolean updateManagerStatus(Integer id, String token) {
//        查询id的信息
        Manager manager = managerMapper.selectById(id);
        if (manager == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        manager.setStatus(manager.getStatus() == 0 ? 1 : 0);
        return managerMapper.updateById(manager) > 0;
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @param checkPassword
     * @return
     */
    @Override
    public long register(String username, String password, String checkPassword) {
        //            检查数据库中是否已有超级管理员
        Long l = managerMapper.selectCount(new QueryWrapper<>());
        if (l > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "已有管理员，无法注册");
        }
        // 1. 校验
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        synchronized (username.intern()) {
            // 账户不能重复
            QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }

            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 插入数据
            Manager manager = new Manager();
            manager.setUsername(username);
            manager.setPassword(encryptPassword);
            manager.setAvatar("http://tangzhe123-com.oss-cn-shenzhen.aliyuncs.com/public/62e2085c2813a.jpg");
//            创建时间更新时间为现在
            manager.setCreateTime(System.currentTimeMillis());
            manager.setUpdateTime(System.currentTimeMillis());
            manager.setIsSuperAdmin(1);
            //生成随机数
            boolean saveResult = this.save(manager);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return manager.getId();
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public ManagerVO login(String username, String password, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", encryptPassword);
        Manager manager = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (manager == null) {
            log.info("user login failed, username cannot match password");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, manager);
        return this.getLoginUserVO(manager);
    }


    /**
     * 获取脱敏信息
     * @param manager
     * @return
     */
    @Override
    public ManagerVO getLoginUserVO(Manager manager) {
        if (manager == null) {
            return null;
        }
        ManagerVO employeesVo = new ManagerVO();
        BeanUtils.copyProperties(manager, employeesVo);
        return employeesVo;
    }

}




