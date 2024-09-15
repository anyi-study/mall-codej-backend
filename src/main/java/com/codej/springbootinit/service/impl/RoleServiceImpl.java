package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.mapper.RoleMapper;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.service.RoleService;
import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-09-15 15:29:07
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

}




