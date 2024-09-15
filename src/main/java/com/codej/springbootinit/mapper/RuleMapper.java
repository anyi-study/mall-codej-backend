package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codej.springbootinit.model.entity.Rule;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 10306
* @description 针对表【rule(规则表)】的数据库操作Mapper
* @createDate 2024-09-15 15:46:15
* @Entity generator.domain.Rule
*/
public interface RuleMapper extends BaseMapper<Rule> {

    List<String> selectRuleNamesByRoleId(Integer roleId);
}




