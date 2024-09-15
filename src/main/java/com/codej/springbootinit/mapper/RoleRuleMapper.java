package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codej.springbootinit.model.entity.RoleRule;
import com.codej.springbootinit.model.enums.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 10306
* @description 针对表【role_rule(角色规则关联表)】的数据库操作Mapper
* @createDate 2024-09-15 15:29:11
* @Entity generator.domain.RoleRule
*/
public interface RoleRuleMapper extends BaseMapper<RoleRule> {

    /**
     * 查询菜单信息
     * @param roleId
     * @return
     */
    @Select("select rule_id from role_rule where role_id = #{roleId}")
    List<Menu> selectMenusByRoleId(Integer roleId);
}




