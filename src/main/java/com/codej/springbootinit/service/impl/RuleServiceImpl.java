package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.mapper.RuleMapper;
import com.codej.springbootinit.model.entity.Rule;
import com.codej.springbootinit.service.RuleService;
import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【rule(规则表)】的数据库操作Service实现
* @createDate 2024-09-15 15:46:15
*/
@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule>
    implements RuleService {

}




