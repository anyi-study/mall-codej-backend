package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.RoleMapper;
import com.codej.springbootinit.model.dto.role.RoleRequest;
import com.codej.springbootinit.model.dto.role.UpdateRoleRequest;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.Rule;
import com.codej.springbootinit.model.vo.RolePageVo;
import com.codej.springbootinit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 10306
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-09-15 15:29:07
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {
@Autowired
private RoleMapper roleMapper;
    /**
     * 添加角色
     * @param roleRequest
     * @return
     */
    @Override
    public Role addRole(RoleRequest roleRequest) {
//        查询是否有这个名字,返回数值
        Integer count  = roleMapper.selectRoleByName(roleRequest.getName());
        if (count != null && count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"角色名已存在");
        }
        Role role = new Role();
        role.setDescription(roleRequest.getDescription());
        role.setName(roleRequest.getName());
        role.setStatus(roleRequest.getStatus());
        role.setCreateTime(System.currentTimeMillis());
        role.setUpdateTime(System.currentTimeMillis());
        int insert = roleMapper.insert(role);
        System.out.println("insert"+insert);
        if (insert <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"添加角色失败");
        }
        return role;
    }

    /**
     * 修改角色
     * @param id
     * @param updateRoleRequest
     * @return
     */
    @Override
    public Boolean updateRole(Integer id, UpdateRoleRequest updateRoleRequest) {
//        查询是否有这个名字,返回数值
        Integer count  = roleMapper.selectRoleByName(updateRoleRequest.getName());
        if (count != null && count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"角色名已存在");
        }
        Role role = new Role();
        role.setId(id);
        role.setDescription(updateRoleRequest.getDescription());
        role.setName(updateRoleRequest.getName());
        role.setStatus(updateRoleRequest.getStatus());
        role.setUpdateTime(System.currentTimeMillis());
        int update = roleMapper.updateById(role);
        if (update <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"修改角色失败");
        }
        return true;
    }

    /**
     * 获取角色列表
     *
     * @param page
     * @return
     */
    @Override
    public RolePageVo getRole(Integer page) {
        // 调用MyBatis-Plus进行分页查询
        Page<Role> managerPage = new Page<>(page, 10);
        IPage<Role> roles = roleMapper.selectPage(managerPage,null);
        // 获取角色及其规则
        List<Rule> rules = null;
        for (Role role : roles.getRecords()) {
            rules = roleMapper.selectRulesByRoleId(role.getId());
            role.setRules(rules);
        }
        // 返回分页数据
        return new RolePageVo(roles.getRecords(), roles.getTotal(), rules);
    }

    /**
     * 删除角色
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
        return roleMapper.deleteById(id) > 0;
    }

    /**
     * 修改角色状态
     * @param id
     * @param token
     * @return
     */
    @Override
    public boolean updateRoleStatus(Integer id, String token) {
//        查询id的信息
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        role.setStatus(role.getStatus() == 0 ? 1 : 0);
        return roleMapper.updateById(role) > 0;
    }

    /**
     * 配置角色权限
     * @param id
     * @param ruleIds
     * @return
     */
    @Override
    @Transactional // 添加事务管理
    public boolean setRoleRules(Integer id, List<Integer> ruleIds) {
        try {
            // 校验参数
            if (id == null || ruleIds == null || ruleIds.isEmpty()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }

            Role role = roleMapper.selectById(id);
            if (role == null) {
                throw new BusinessException(ErrorCode.ID_ERROR);
            }

            // 先删除该角色的所有旧的规则关联
            roleMapper.deleteRoleRulesByRoleId(id);

            // 重新插入新的规则关联
            roleMapper.insertRoleRules(id, ruleIds);

            return true;
        } catch (Exception e) {
            // 捕获异常并回滚事务
            // Spring 会自动回滚事务
            return false;
        }
    }


}




