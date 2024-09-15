package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.mapper.RoleRuleMapper;
import com.codej.springbootinit.model.entity.RoleRule;
import com.codej.springbootinit.service.RoleRuleService;

import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【role_rule(角色规则关联表)】的数据库操作Service实现
* @createDate 2024-09-15 15:29:11
*/
@Service
public class RoleRuleServiceImpl extends ServiceImpl<RoleRuleMapper, RoleRule>
    implements RoleRuleService {

}




