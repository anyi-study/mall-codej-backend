package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.Rule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


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

    /**
     * 根据角色名查询角色
     * @param name
     * @return
     */
    Integer  selectRoleByName(String name);


    IPage<Role> selectRolePage(Page<Role> managerPage);

    List<Rule> selectRulesByRoleId(Integer id);

    // 删除角色的所有规则关联
    @Delete("DELETE FROM role_rule WHERE role_id = #{roleId}")
    void deleteRoleRulesByRoleId(@Param("roleId") Integer roleId);

    // 插入新的角色-规则关联
    void insertRoleRules(@Param("roleId") Integer roleId, @Param("ruleIds") List<Integer> ruleIds);

}




