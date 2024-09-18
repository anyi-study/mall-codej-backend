package com.codej.springbootinit.controller.userLevel;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import com.codej.springbootinit.model.vo.UserLevelPageVo;
import com.codej.springbootinit.model.vo.UserPageVo;
import com.codej.springbootinit.service.UserLevelService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "会员等级管理")
@RestController
@RequestMapping("/admin/user_level")
@Slf4j
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    /**
     * 删除会员等级
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除会员等级")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteUserLevel(@PathVariable Integer id,
                                            @RequestHeader("token") String token) {
        //参数校验及身份验证
//        id判空
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = userLevelService.deleteUserLevel(id);
        if (!b) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(true, "ok");
    }

    @ApiOperation("获取会员等级列表")
    @GetMapping("/{page}")
    public BaseResponse<UserLevelPageVo> getUserLevelList(
            @RequestHeader("token") String token,
            @PathVariable("page") int page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
// 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (page <= 0 || limit <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserLevelPageVo userPageVo = userLevelService.getUserLevelList(page, limit);
        return ResultUtils.success(userPageVo);
    }

    @PostMapping("/{id}/update_status")
    @ApiOperation("修改会员等级状态")
    public BaseResponse<Boolean> updateUserLevelStatus(@RequestHeader("token") String token,
                                             @RequestParam("status") Integer status,
                                             @PathVariable("id") Integer id) {
        //参数校验及身份验证
        if (status == null && id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        if (userLevelService.getById(id) == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id有误，会员等级不存在");
        }
        Boolean b = userLevelService.updateUserLevelStatus(status, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新会员等级失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/{id}")
    @ApiOperation("修改会员等级")
    public BaseResponse<Boolean> updateUserLevel(@RequestHeader("token") String token,
                                                 @PathVariable("id") Integer id,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("level") Integer level,
                                                 @RequestParam("status") Integer status,
                                                 @RequestParam("discount") Integer discount,
                                                 @RequestParam("max_price") Integer maxPrice,
                                                 @RequestParam("max_times") Integer maxTimes) {
        // 参数校验及身份验证
        if (name == null || level == null || status == null || discount == null || maxPrice == null || maxTimes == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (name.length() < 3 || level < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "会员等级名称或等级权重不正确");
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }

        // 调用服务层方法更新会员等级
        Boolean result = userLevelService.updateUserLevel(id, name, level, status, discount, maxPrice, maxTimes);
        if (!result) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新会员等级失败");
        }

        return ResultUtils.success(true);
    }


    @PostMapping
    @ApiOperation("添加会员等级")
    public BaseResponse<UserLevel> addUserLevel(
            @RequestHeader("token") String token,
            @RequestParam("name") String name,
            @RequestParam("level") Integer level,
            @RequestParam("status") Integer status,
            @RequestParam("discount") Integer discount,
            @RequestParam("max_price") Integer maxPrice,
            @RequestParam("max_times") Integer maxTimes) {
        //参数校验及身份验证
//        参数判空
        if (name == null || level == null || status == null || discount == null || maxPrice == null || maxTimes == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        UserLevel newUser = userLevelService.addUserLevel(name, level, status, discount, maxPrice, maxTimes);
        if (newUser == null){
            return ResultUtils.error(ErrorCode.USERNAME_ERROR);
        }
        return ResultUtils.success(newUser);
    }
}
