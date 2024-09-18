package com.codej.springbootinit.mapper;

import com.codej.springbootinit.model.entity.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 10306
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-09-18 22:07:30
* @Entity com.codej.springbootinit.model.entity.user.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getByName(String username);
}




