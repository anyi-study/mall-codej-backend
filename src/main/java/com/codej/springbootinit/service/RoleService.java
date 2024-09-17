package com.codej.springbootinit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.model.dto.role.RoleRequest;
import com.codej.springbootinit.model.dto.role.UpdateRoleRequest;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.Rule;
import com.codej.springbootinit.model.vo.RolePageVo;

import java.util.List;

/**
* @author 10306
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2024-09-15 15:29:07
*/
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     * @param roleRequest
     * @return
     */
    Role addRole(RoleRequest roleRequest);

    /**
     * 修改角色
     * @param id
     * @param updateRoleRequest
     * @return
     */
    Boolean updateRole(Integer id, UpdateRoleRequest updateRoleRequest);

    /**
     * 获取角色列表
     * @param page
     * @return
     */
    RolePageVo getRole(Integer page);

    /**
     * 删除角色
     * @param id
     * @param token
     * @return
     */
    boolean deleteManager(Integer id, String token);

    /**
     * 修改角色状态
     * @param id
     * @param token
     * @return
     */
    boolean updateRoleStatus(Integer id, String token);

    /**
     * 配置角色权限
     * @param id
     * @param ruleIds
     * @return
     */
    boolean setRoleRules(Integer id, List<Integer> ruleIds);

}
