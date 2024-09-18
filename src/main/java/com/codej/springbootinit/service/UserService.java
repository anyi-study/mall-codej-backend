package com.codej.springbootinit.service;

import com.codej.springbootinit.model.entity.user.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.vo.UserPageVo;

/**
* @author 10306
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-09-18 22:07:30
*/
public interface UserService extends IService<User> {

    /**
     * 获取用户列表
     * @param page
     * @param limit
     * @param keyword
     * @param userLevelId
     * @return
     */
    UserPageVo getUserList(int page, int limit, String keyword, Integer userLevelId);

    /**
     * 添加用户
     * @param username
     * @param password
     * @param status
     * @param nickname
     * @param phone
     * @param email
     * @param avatar
     * @param userLevelId
     * @return
     */
    User addUser(String username, String password, Integer status, String nickname, String phone, String email, String avatar, Integer userLevelId);

    /**
     * 修改用户
     * @param id
     * @param username
     * @param password
     * @param status
     * @param nickname
     * @param phone
     * @param email
     * @param avatar
     * @param userLevelId
     * @return
     */
    Boolean updateUser(Integer id, String username, String password, Integer status, String nickname, String phone, String email, String avatar, Integer userLevelId);

    /**
     * 修改用户状态
     * @param status
     * @param id
     * @return
     */
    Boolean updateUserStatus(Integer status, Integer id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Boolean deleteUser(Integer id);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getByName(String username);
}
