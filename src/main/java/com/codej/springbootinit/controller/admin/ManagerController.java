package com.codej.springbootinit.controller.admin;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.manager.AdminLoginRequest;
import com.codej.springbootinit.model.dto.manager.ManagerRegisterRequest;
import com.codej.springbootinit.model.dto.manager.UpdateManagerRequest;
import com.codej.springbootinit.model.dto.manager.UpdatePasswordManager;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.vo.ManagerPageVo;
import com.codej.springbootinit.model.vo.ManagerVO;
import com.codej.springbootinit.model.vo.UserPermissionsResponse;
import com.codej.springbootinit.service.ManagerService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Api(tags  = "管理员接口")
@RestController
@RequestMapping("/admin")
@Slf4j
public class ManagerController {
    @Resource
    private ManagerService managerService;

    @ApiOperation("修改管理员状态")
    @PostMapping("/{id}/update_status")
    public BaseResponse<Boolean> updateManagerStatus(
            @PathVariable("id") Integer id,
            @RequestHeader("token") String token) {
//        参数校验及身份验证
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }

        // 验证 token 或获取用户信息（示例中略过）
//        根据username获取用户信息 todo 验证超级管理员
        Manager tokenManager = managerService.getByName(tokenUsername);
        if (tokenManager == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用服务层方法修改用户状态
        boolean success = managerService.updateManagerStatus(id, token);
        // 返回成功响应
        if (!success) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(true);
    }

    @ApiOperation("删除管理员")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteManager(
            @PathVariable("id") Integer id,
            @RequestHeader("token") String token) {
//        参数校验及身份验证
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }

        // 验证 token 或获取用户信息（示例中略过）
//        根据username获取用户信息 todo 验证超级管理员
        Manager tokenManager = managerService.getByName(tokenUsername);
        if (tokenManager == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用服务层方法修改用户
        boolean success = managerService.deleteManager(id, token);
        // 返回成功响应
        if (!success) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(true);
    }

    @ApiOperation("修改管理员信息")
    @PostMapping("/manager/{id}")
    public BaseResponse<Boolean> updateManager(
            @PathVariable("id") Integer id,
            @RequestHeader("token") String token,
            @RequestBody UpdateManagerRequest updateManagerRequest) {

        String username = updateManagerRequest.getUsername();
        String password = updateManagerRequest.getPassword();
        String avatar = updateManagerRequest.getAvatar();
        Integer roleId = updateManagerRequest.getRoleId();
        Integer status = updateManagerRequest.getStatus();
//        参数校验及身份验证
        if (id == null || avatar == null || username == null || password == null || roleId == null || status == null) {
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

        // 验证 token 或获取用户信息（示例中略过）
//        根据username获取用户信息 todo 验证超级管理员
        Manager tokenManager = managerService.getByName(tokenUsername);
        if (tokenManager == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用服务层方法修改用户
        boolean success = managerService.updateManager(id, username, password, roleId, status, avatar, token);
        // 返回成功响应
        return ResultUtils.success(success);
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<String> logout(@RequestHeader("token") String token) {
        // 校验 token 是否有效
        String tokenUsername = JwtUtil.extractUsername(token);

        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "非法token，请先登录！");
        }
//        todo 后续可添加redis记录token，这里执行清除操作
        // 可以在这里执行任何需要在退出登录时处理的逻辑（例如将 token 加入黑名单）
        // 通常情况下，不需要做额外处理，JWT 是无状态的，直接让客户端删除 token 即可

        // 返回成功信息
        return ResultUtils.success("退出登录成功");
    }

    /**
     * 当前用户修改密码
     *
     * @param token
     * @param updatePasswordManager
     * @return
     */

    @ApiOperation(value = "修改密码")
    @PostMapping("/updatepassword")
    public BaseResponse<String> updatePassword(@RequestHeader("token") String token,
                                               @RequestBody UpdatePasswordManager updatePasswordManager) {
        String newPassword = updatePasswordManager.getPassword();
        String oldpassword = updatePasswordManager.getOldpassword();
        String repassword = updatePasswordManager.getRepassword();
        // 参数校验
        if (newPassword == null || oldpassword == null
                || repassword == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 检查新密码是否符合长度要求
        if (newPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码长度必须至少为8位");
        }
        // 新密码和旧密码不能相同
        if (oldpassword.equals(newPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码和旧密码不能相同");
        }
        // 检查新密码与重复密码是否一致
        if (!newPassword.equals(repassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }


        // 验证 token 或获取用户信息（示例中略过）
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }


        // 调用服务层方法修改密码
        managerService.updatePassword(username, oldpassword, newPassword);

        // 返回成功响应
        return ResultUtils.success("密码修改成功");
    }

    /**
     * 添加管理员
     *
     * @param token
     * @param username
     * @param password
     * @param roleId
     * @param status
     * @param avatar
     * @return
     */
    @ApiOperation("添加管理员")
    @PostMapping
    public BaseResponse<Manager> addManager(
            @RequestHeader("token") String token,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role_id") Integer roleId,
            @RequestParam("status") Integer status,
            @RequestParam(value = "avatar", required = false) String avatar) {

        //参数校验及身份验证
        if (username == null || password == null || roleId == null || status == null) {
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

        // 验证 token 或获取用户信息（示例中略过）
//        根据username获取用户信息
        Manager tokenManager = managerService.getByName(tokenUsername);
        if (tokenManager == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 调用服务层方法添加用户
        Manager manager = managerService.addManager(username, password, roleId, status, avatar, token);

        // 返回成功响应
        return ResultUtils.success(manager);
    }


    /**
     * 管理员获取管理员信息
     *
     * @param token
     * @param page
     * @param limit
     * @param keyword
     * @param request
     * @return
     */
    @ApiOperation("获取管理员信息")
    @GetMapping("/{page}")
    public BaseResponse<ManagerPageVo> getManagers(@RequestHeader(value = "token") String token,
                                                    @PathVariable("page") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(value = "keyword", required = false) String keyword,
                                                    HttpServletRequest request) {

        // 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        Manager manager = managerService.getByName(username);
        if (manager == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (page <= 0 || limit <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 调用服务层方法获取分页数据
        ManagerPageVo managerPageVo = managerService.getManagers(page, limit, keyword);

        // 返回成功响应
        return ResultUtils.success(managerPageVo);
    }



    /**
     * 管理员获取管理员信息
     *
     * @param token
     * @param page
     * @param limit
     * @param keyword
     * @param request
     * @return
     */
//    @ApiOperation("获取管理员信息")
//    @GetMapping("/{page}")
//    public BaseResponse<ManagerPageResponse> getManagers(@RequestHeader(value = "token") String token,
//                                                         @PathVariable("page") Integer page,
//                                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit,
//                                                         @RequestParam(value = "keyword", required = false) String keyword,
//                                                         HttpServletRequest request) {
//
//        // 获取请求头中的 token
//        String username = JwtUtil.extractUsername(token);
//        if (username == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
//        // 验证 token 或获取用户信息（示例中略过）
////        根据username获取用户信息
//        Manager manager = managerService.getByName(username);
//        if (manager == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//
//        //        请求参数校验
//        if (page <= 0 || limit <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 调用服务层方法
//        ManagerPageResponse response = managerService.getManagers(page, limit, keyword);
//
//        // 返回成功响应
//        return ResultUtils.success(response);
//    }

    @ApiOperation("获取权限")
    @PostMapping("/getinfo")
    public BaseResponse<UserPermissionsResponse> getUserPermissions(@RequestHeader("token") String token) {
        // 移除 "Bearer " 前缀
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // 获取用户权限信息
        UserPermissionsResponse response = managerService.getUserPermissionsByToken(actualToken);

        // 返回响应
        return ResultUtils.success(response);
    }


    // 注册接口
    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody ManagerRegisterRequest managerRegisterRequest) {
        if (managerRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = managerRegisterRequest.getUsername();
        String password = managerRegisterRequest.getPassword();
        String checkPassword = managerRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            return null;
        }
        long result = managerService.register(username, password, checkPassword);
        return ResultUtils.success(result);
    }

    // 登录接口
    @ApiOperation(value = "登录接口")

//    @PostMapping("/login")//暂不启用 启用先修改拦截器
    public BaseResponse<ManagerVO> userLogin(@RequestBody AdminLoginRequest adminLoginRequest, HttpServletRequest request) {
        if (adminLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = adminLoginRequest.getUsername();
        String password = adminLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ManagerVO employeesVo = managerService.login(username, password, request);
        return ResultUtils.success(employeesVo);
    }

    // 登录接口
    @ApiOperation(value = "token登录接口")

    @PostMapping("/login")
    public BaseResponse<Map<String, String>> userLoginToken(@RequestBody AdminLoginRequest adminLoginRequest, HttpServletRequest request) {
        if (adminLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = adminLoginRequest.getUsername();
        String password = adminLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ManagerVO employeesVo = managerService.login(username, password, request);
//        判空
        if (employeesVo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = JwtUtil.generateToken(employeesVo.getUsername());

        return ResultUtils.success(Collections.singletonMap("token", token));
    }
}
