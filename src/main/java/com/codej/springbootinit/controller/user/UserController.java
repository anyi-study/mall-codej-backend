package com.codej.springbootinit.controller.user;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.vo.UserPageVo;
import com.codej.springbootinit.service.UserService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 删除规格
     *
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除用户")
    @PostMapping("/{id}/delete")
    public BaseResponse<Integer> deleteSkus(@PathVariable Integer id,
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
        Boolean b = userService.deleteUser(id);
        if (!b) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(0, "ok");
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/{page}")
    public BaseResponse<UserPageVo> getUserList(
            @RequestHeader("token") String token,
            @PathVariable("page") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "user_level_id", required = false) Integer userLevelId) {
// 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

//        User user = userService.getByName(username);
//        if (user == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }

        if (page <= 0 || limit <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserPageVo userPageVo = userService.getUserList(page, limit, keyword, userLevelId);
        return ResultUtils.success(userPageVo);
    }

    @PostMapping("/{id}/update_status")
    @ApiOperation("修改用户状态")
    public BaseResponse<Boolean> updateUserStatus(@RequestHeader("token") String token,
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
        if (userService.getById(id) == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id有误，用户不存在");
        }
        Boolean b = userService.updateUserStatus(status, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新规格失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/{id}")
    @ApiOperation("修改用户")
    public BaseResponse<Boolean> updateUser(@RequestHeader("token") String token,
                                            @PathVariable("id") Integer id,
                                            @RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("status") Integer status,
                                            @RequestParam(value = "nickname", required = false) String nickname,
                                            @RequestParam(value = "phone", required = false) String phone,
                                            @RequestParam(value = "email", required = false) String email,
                                            @RequestParam(value = "avatar", required = false) String avatar,
                                            @RequestParam("user_level_id") Integer userLevelId) {
        //参数校验及身份验证
//        参数判空
        if (username == null || password == null || userLevelId == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (username.length() < 4 || password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = userService.updateUser(id, username, password, status, nickname, phone, email, avatar, userLevelId);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新规格失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping
    @ApiOperation("添加用户")
    public BaseResponse<User> addUser(
            @RequestHeader("token") String token,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("status") Integer status,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam("user_level_id") Integer userLevelId) {
        //参数校验及身份验证
//        参数判空
        if (username == null || password == null || userLevelId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (username.length() < 4 || password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        User newUser = userService.addUser(username, password, status, nickname, phone, email, avatar, userLevelId);
        if (newUser == null){
            return ResultUtils.error(ErrorCode.USERNAME_ERROR);
        }
        return ResultUtils.success(newUser);
    }
}
