package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codej.springbootinit.model.entity.Manager;
import org.apache.ibatis.annotations.Select;


/**
* @author 10306
* @description 针对表【manager(管理员表)】的数据库操作Mapper
* @createDate 2024-09-15 13:39:44
* @Entity generator.domain.Manager
*/
public interface ManagerMapper extends BaseMapper<Manager> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Select("select * from manager where username = #{username}")
    Manager selectByUsername(String username);
}




