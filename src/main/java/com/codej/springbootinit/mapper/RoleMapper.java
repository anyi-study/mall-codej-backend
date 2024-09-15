package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codej.springbootinit.model.entity.Role;

import java.util.List;


/**
* @author 10306
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2024-09-15 15:29:07
* @Entity generator.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色id查询角色信息
     * @param roleId
     * @return
     */
    Role selectRoleById(Integer roleId);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> selectAllRoles();
}




