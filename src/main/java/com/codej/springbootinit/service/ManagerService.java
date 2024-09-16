package com.codej.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.ManagerPageResponse;
import com.codej.springbootinit.model.vo.ManagerPageVo;
import com.codej.springbootinit.model.vo.ManagerVO;
import com.codej.springbootinit.model.vo.UserPermissionsResponse;

import javax.servlet.http.HttpServletRequest;

/**
* @author 10306
* @description 针对表【manager(管理员表)】的数据库操作Service
* @createDate 2024-09-15 13:39:44
*/
public interface ManagerService extends IService<Manager> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long register(String userAccount, String userPassword, String checkPassword);

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    ManagerVO login(String username, String password, HttpServletRequest request);

    /**
     * 获取脱敏信息
     * @param manager
     * @return
     */
    ManagerVO getLoginUserVO(Manager manager);

    /**
     * 获取用户权限
     * @param token
     * @return
     */
    UserPermissionsResponse getUserPermissionsByToken(String token);

    /**
     * 获取管理员列表
     * @param page
     * @param limit
     * @param keyword
     * @return
     */
//    ManagerPageResponse getManagers(Integer page, Integer limit, String keyword);

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
    ManagerPageVo getManagers(int page, int limit, String keyword);

    /**
     * 根据用户名获取管理员信息
     * @param username
     * @return
     */
    Manager getByName(String username);

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
    Manager addManager(String username, String password, Integer roleId, Integer status, String avatar, String token);

    /**
     * 修改密码
     * @param username
     * @param oldpassword
     * @param newPassword
     */
    void updatePassword(String username, String oldpassword, String newPassword);

    /**
     * 修改管理员信息
     * @param username
     * @param password
     * @param roleId
     * @param status
     * @param avatar
     * @param token
     * @return
     */
    Boolean updateManager(Integer id,String username, String password, Integer roleId, Integer status, String avatar, String token);

    /**
     * 删除管理员
     * @param id
     * @param token
     * @return
     */
    boolean deleteManager(Integer id, String token);

    /**
     * 修改管理员状态
     * @param id
     * @param token
     * @return
     */
    boolean updateManagerStatus(Integer id, String token);
}
