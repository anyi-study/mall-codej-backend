package com.codej.springbootinit.service;

import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.vo.UserLevelPageVo;

/**
* @author 10306
* @description 针对表【user_level(会员等级表)】的数据库操作Service
* @createDate 2024-09-18 22:54:33
*/
public interface UserLevelService extends IService<UserLevel> {
    /**
     * 获取会员等级列表
     * @param page
     * @param limit
     * @return
     */
    UserLevelPageVo getUserLevelList(int page, int limit);

    /**
     * 添加
     * @param name
     * @param level
     * @param status
     * @param discount
     * @param maxPrice
     * @param maxTimes
     * @return
     */
    UserLevel addUserLevel(String name, Integer level, Integer status, Integer discount, Integer maxPrice, Integer maxTimes);

    /**
     * 修改会员等级
     * @param id
     * @param name
     * @param level
     * @param status
     * @param discount
     * @param maxPrice
     * @param maxTimes
     * @return
     */

    Boolean updateUserLevel(Integer id, String name, Integer level, Integer status, Integer discount, Integer maxPrice, Integer maxTimes);

    /**
     * 修改会员等级状态
     * @param status
     * @param id
     * @return
     */
    Boolean updateUserLevelStatus(Integer status, Integer id);

    /**
     * 删除会员等级
     * @param id
     * @return
     */
    Boolean deleteUserLevel(Integer id);

}
