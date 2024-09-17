package com.codej.springbootinit.controller.skus;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.skus.SkusRequest;
import com.codej.springbootinit.model.entity.skus.Skus;
import com.codej.springbootinit.model.vo.SkusPageVo;
import com.codej.springbootinit.service.SkusService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "规格管理")
@RestController
@RequestMapping("/admin/skus")
@Slf4j
public class SkusController {
    @Autowired
    private SkusService skusService;

    /**
     * 删除规格
     * @param ids
     * @param token
     * @return
     */
    @ApiOperation("删除规格")
    @PostMapping("/delete_all")
    public BaseResponse<Integer> deleteSkus(@RequestBody List<Integer> ids,
                                            @RequestHeader("token") String token) {
        //参数校验及身份验证
//        ids参数校验 数组判空
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = skusService.deleteSkusAll(ids);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "删除规格失败");
        }
        return ResultUtils.success(0,"ok");
    }

    @ApiOperation("获取规格列表")
    @GetMapping("/{page}")
    public BaseResponse<SkusPageVo> getSkusList(@RequestHeader(value = "token") String token,
                                                @PathVariable("page") Integer page,
                                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        // 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (page <= 0 || limit <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 调用服务层方法获取分页数据
        SkusPageVo skusPageVo = skusService.getSkusList(page, limit);

        // 返回成功响应
        return ResultUtils.success(skusPageVo);
    }

    @PostMapping("/{id}/update_status")
    @ApiOperation("修改商品规格状态")
    public BaseResponse<Boolean> updateSkuss(@RequestHeader("token") String token,
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
        if (skusService.getById(id) == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id有误，规格不存在");
        }
        Boolean b = skusService.updateSkusStatus(status, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新规格失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/{id}")
    @ApiOperation("修改规格")
    public BaseResponse<Boolean> updateSkus(@RequestHeader("token") String token,
                                            @RequestBody SkusRequest skusRequest,
                                            @PathVariable("id") Integer id) {
        //参数校验及身份验证
        if (skusRequest == null && id == null) {
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
        Boolean b = skusService.updateSkus(skusRequest, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新规格失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping
    @ApiOperation("添加规格")
    public BaseResponse<Skus> addSkus(@RequestHeader("token") String token,
                                      @RequestBody SkusRequest skusRequest) {
        //参数校验及身份验证
        if (skusRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Skus skus = skusService.addSkus(skusRequest);
        if (skus == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "添加规格失败");
        }
        return ResultUtils.success(skus);
    }
}
