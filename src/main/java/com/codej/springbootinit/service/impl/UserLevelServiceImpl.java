package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import com.codej.springbootinit.service.UserLevelService;
import com.codej.springbootinit.mapper.UserLevelMapper;
import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【user_level(用户等级表)】的数据库操作Service实现
* @createDate 2024-09-18 22:54:33
*/
@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel>
    implements UserLevelService{

}




