package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.UserMapper;
import com.codej.springbootinit.model.entity.coupon.Coupon;
import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import com.codej.springbootinit.model.vo.CouponPageVo;
import com.codej.springbootinit.model.vo.UserLevelPageVo;
import com.codej.springbootinit.model.vo.UserPageVo;
import com.codej.springbootinit.service.UserLevelService;
import com.codej.springbootinit.mapper.UserLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
* @author 10306
* @description 针对表【user_level(用户等级表)】的数据库操作Service实现
* @createDate 2024-09-18 22:54:33
*/
@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel>
    implements UserLevelService{


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
     * @return
     */
    @Override
    public UserLevelPageVo getUserLevelList(int page, int limit) {
        // 调用MyBatis-Plus进行分页查询
        // 示例代码，具体实现需要根据实际数据库和业务逻辑调整
        Page<UserLevel> couponPage = new Page<>(page, limit);
        LambdaQueryWrapper<UserLevel> queryWrapper = new LambdaQueryWrapper<>();
        IPage<UserLevel> result = userLevelMapper.selectPage(couponPage, queryWrapper);
        // 返回分页数据
        return new UserLevelPageVo(result.getRecords(), result.getTotal());
    }

    /**
     * 添加会员等级
     * @param name
     * @param level
     * @param status
     * @param discount
     * @param maxPrice
     * @param maxTimes
     * @return
     */
    @Override
    public UserLevel addUserLevel(String name, Integer level, Integer status, Integer discount, Integer maxPrice, Integer maxTimes) {
        // 构建新用户对象
        UserLevel userLevel = new UserLevel();
        // 添加用户等级字段
        userLevel.setName(name);
        userLevel.setLevel(level);
        userLevel.setStatus(status);
        userLevel.setDiscount(discount);
        userLevel.setMaxPrice(maxPrice);
        userLevel.setMaxTimes(maxTimes);
        // 插入用户到数据库
        int insert = userLevelMapper.insert(userLevel);
        if (insert <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户等级创建失败");
        }
        return userLevel;
    }

    /**
     * 修改用户
     * @param id
     * @param name
     * @param level
     * @param status
     * @param discount
     * @param maxPrice
     * @param maxTimes
     * @return
     */
    @Override
    public Boolean updateUserLevel(Integer id, String name, Integer level, Integer status, Integer discount, Integer maxPrice, Integer maxTimes) {
        // 查找用户等级信息
        UserLevel userLevel = userLevelMapper.selectById(id);
        if (userLevel == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未找到指定的用户等级");
        }

        // 更新用户等级字段
        userLevel.setName(name);
        userLevel.setLevel(level);
        userLevel.setStatus(status);
        userLevel.setDiscount(discount);
        userLevel.setMaxPrice(maxPrice);
        userLevel.setMaxTimes(maxTimes);

        // 执行更新操作
        int update = userLevelMapper.updateById(userLevel);
        if (update <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户等级修改失败");
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
    public Boolean updateUserLevelStatus(Integer status, Integer id) {
        UserLevel userLevel = userLevelMapper.selectById(id);
        userLevel.setStatus(status);
        int update = userLevelMapper.updateById(userLevel);
        if (update <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户等级状态修改失败");
        }
        return true;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public Boolean deleteUserLevel(Integer id) {
        UserLevel userLevel = userLevelMapper.selectById(id);
        if (userLevel == null){
            return false;
        }
        int delete = userLevelMapper.deleteById(id);
        if (delete <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户等级删除失败");
        }
        return true;
    }
}




