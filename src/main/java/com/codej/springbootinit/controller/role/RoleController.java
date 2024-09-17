package com.codej.springbootinit.controller.role;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.role.RoleRequest;
import com.codej.springbootinit.model.dto.role.RoleRuleRequest;
import com.codej.springbootinit.model.dto.role.UpdateRoleRequest;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.vo.RolePageVo;
import com.codej.springbootinit.service.RoleService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "角色管理")
@RequestMapping("/admin/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 配置角色权限
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("配置角色权限")
    @PostMapping("/set_rules")
    public BaseResponse<Boolean> setRoleRules(@RequestBody RoleRuleRequest request,
            @RequestHeader("token") String token) {
//        参数校验及身份验证
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        // 调用服务层方法修改用户状态
        boolean result = roleService.setRoleRules(request.getId(), request.getRuleIds());
        // 返回成功响应
        return ResultUtils.success(result);
    }

    /**
     * 修改角色状态
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("修改角色状态")
    @PostMapping("/{id}/update_status")
    public BaseResponse<Boolean> updateRoleStatus(
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
        // 调用服务层方法修改用户状态
        boolean success = roleService.updateRoleStatus(id, token);
        // 返回成功响应
        if (!success) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(true);
    }
    /**
     * 删除角色
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除角色")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteRole(
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
//        根据username获取用户信息 todo 检查每个controller身份鉴权问题
        // 调用服务层方法修改用户
        boolean success = roleService.deleteManager(id, token);
        // 返回成功响应
        if (!success) {
            return ResultUtils.error(ErrorCode.ID_ERROR);
        }
        return ResultUtils.success(true);
    }

    @ApiOperation("获取角色列表")
    @GetMapping("/{page}")
    public BaseResponse<RolePageVo> getRole(@RequestHeader(value = "token") String token,
                                            @PathVariable("page") Integer page) {
        // 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (page <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 调用服务层方法获取分页数据
        RolePageVo rolePage = roleService.getRole(page);

        // 返回成功响应
        return ResultUtils.success(rolePage);
    }

    @ApiOperation("修改角色")
    @PostMapping("/{id}")
    public BaseResponse<Boolean> updateRole(
            @PathVariable("id") Integer id,
            @RequestHeader("token") String token,
            @RequestBody UpdateRoleRequest updateRoleRequest) {

        String name = updateRoleRequest.getName();
        Integer status = updateRoleRequest.getStatus();
        String description = updateRoleRequest.getDescription();
//        参数校验及身份验证
        if (id == null || name == null || description == null  || status == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }

        Boolean b = roleService.updateRole(id,updateRoleRequest);
        if (!b) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(true);
    }
    @ApiOperation("添加角色")
    @PostMapping
    public BaseResponse<Role> addRole(@RequestHeader("token") String token,
                                      @RequestBody RoleRequest roleRequest){
//        身份校验
        if (token == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
//        参数校验
        if (roleRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        调用服务处理层方法
        Role role = roleService.addRole(roleRequest);
        if (role != null){
            return ResultUtils.success(role);
        }
        return ResultUtils.error(400, "添加角色失败");
    }

}
