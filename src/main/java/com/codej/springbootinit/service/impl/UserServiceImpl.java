package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.UserLevelMapper;
import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import com.codej.springbootinit.model.vo.UserPageVo;
import com.codej.springbootinit.service.UserService;
import com.codej.springbootinit.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
* @author 10306
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-09-18 22:07:30
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLevelMapper userLevelMapper;


    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "codeJ";

    /**
     * 获取用户列表
     * @param page
     * @param limit
     * @param keyword
     * @param userLevelId
     * @return
     */
    @Override
    public UserPageVo getUserList(int page, int limit, String keyword, Integer userLevelId) {
        // 设置分页条件
        Page<User> pageCondition = new Page<>(page, limit);

        // 构建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.lambda().like(User::getPhone, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getUsername, keyword);
        }
        if (userLevelId != null) {
            queryWrapper.lambda().eq(User::getUserLevelId, userLevelId);
        }

        // 执行分页查询
        IPage<User> userIPage = this.page(pageCondition, queryWrapper);

        // 构造返回数据
        List<User> users = userIPage.getRecords();
        long totalCount = userIPage.getTotal();
        List<UserLevel> userLevel = userLevelMapper.selectList(null);
        return new UserPageVo(users, totalCount, userLevel);
    }

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
    @Override
    public User addUser(String username, String password, Integer status, String nickname, String phone, String email, String avatar, Integer userLevelId) {
        // 构建新用户对象
        User user = new User();
        user.setUsername(username);
//        加密
        password = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        user.setPassword(password);  // 对密码进行加密
        user.setStatus(status);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatar(avatar);
        user.setUserLevelId(userLevelId);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        if (userMapper.getByName(username) != null) {
            return null;
        }
        // 插入用户到数据库
        int insert = userMapper.insert(user);
        if (insert <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户创建失败");
        }
        return user;
    }

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
    @Override
    public Boolean updateUser(Integer id, String username, String password, Integer status, String nickname, String phone, String email, String avatar, Integer userLevelId) {
        User user = userMapper.selectById(id);
        user.setUsername(username);
        password = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        user.setPassword(password);
        user.setStatus(status);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatar(avatar);
        user.setUserLevelId(userLevelId);
        user.setUpdateTime(System.currentTimeMillis());
        int update = userMapper.updateById(user);
        if (update <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户修改失败");
        }
        return true;
    }

    /**
     * 修改用户状态
     * @param status
     * @param id
     * @return
     */
    @Override
    public Boolean updateUserStatus(Integer status, Integer id) {
        User user = userMapper.selectById(id);
        user.setStatus(status);
        int update = userMapper.updateById(user);
        if (update <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户状态修改失败");
        }
        return true;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Boolean deleteUser(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null){
            return false;
        }
        int delete = userMapper.deleteById(id);
        if (delete <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户删除失败");
        }
        return true;
    }

    @Override
    public User getByName(String username) {
        return userMapper.getByName(username);
    }
}




