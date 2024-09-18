package com.codej.springbootinit.controller.coupon;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.coupon.Coupon;
import com.codej.springbootinit.model.vo.CouponPageVo;
import com.codej.springbootinit.model.vo.UserLevelPageVo;
import com.codej.springbootinit.service.CouponService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/admin/coupon")
@RestController
@Slf4j
@Api(tags = "优惠券管理")
public class CouponController {
    @Autowired
    private CouponService couponService;
    /**
     * 失效优惠券
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("失效优惠券")
    @PostMapping("/{id}/update_status")
    public BaseResponse<Boolean> updateCouponStatus(@PathVariable Integer id,
                                              @RequestHeader("token") String token) {
        //参数校验及身份验证
//        id
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
        Boolean b = couponService.updateCouponStatus(id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "失效优惠券失败");
        }
        return ResultUtils.success(true,"ok");
    }
    /**
     * 删除优惠券
     * @param id
     * @param token
     * @return
     */
    @ApiOperation("删除优惠券")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteCoupon(@PathVariable Integer id,
                                            @RequestHeader("token") String token) {
        //参数校验及身份验证
//        id
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
        Boolean b = couponService.deleteCouponById(id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "删除优惠券失败");
        }
        return ResultUtils.success(true,"ok");
    }

    @ApiOperation("获取优惠券列表")
    @GetMapping("/{page}")
    public BaseResponse<CouponPageVo> getUserLevelList(@RequestHeader(value = "token") String token,
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
        CouponPageVo couponPageVo = couponService.getUserLevelList(page, limit);

        // 返回成功响应
        return ResultUtils.success(couponPageVo);
    }

    /**
     * 修改优惠券
     *
     * @param name
     * @param type
     * @param value
     * @param total
     * @param minPrice
     * @param startTime
     * @param endTime
     * @param orders
     * @param token
     * @return
     */
    @ApiOperation("修改优惠券")
    @PostMapping("/{id}")
    public BaseResponse<Boolean> updateCoupon(@PathVariable("id") Integer id,
                                             @RequestParam("name") String name,
                                             @RequestParam("type") Integer type,
                                             @RequestParam("value") BigDecimal value,
                                             @RequestParam("total") Integer total,
                                             @RequestParam("min_price") BigDecimal minPrice,
                                             @RequestParam("start_time") Integer startTime,
                                             @RequestParam("end_time") Integer endTime,
                                             @RequestParam("orders") Integer orders,
                                             @RequestHeader String token) {
//        参数校验

        if (id == null || name == null || type == null || value == null || total == null || minPrice == null || startTime == null || endTime == null || orders == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
//        身份验证
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = couponService.updateCoupon(id,name, type, value, total, minPrice, startTime, endTime, orders);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "修改优惠券失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 添加优惠券
     *
     * @param name
     * @param type
     * @param value
     * @param total
     * @param minPrice
     * @param startTime
     * @param endTime
     * @param orders
     * @param token
     * @return
     */
    @ApiOperation("添加优惠券")
    @PostMapping
    public BaseResponse<Coupon> addCoupon(@RequestParam("name") String name,
                                          @RequestParam("type") Integer type,
                                          @RequestParam("value") BigDecimal value,
                                          @RequestParam("total") Integer total,
                                          @RequestParam("min_price") BigDecimal minPrice,
                                          @RequestParam("start_time") Integer startTime,
                                          @RequestParam("end_time") Integer endTime,
                                          @RequestParam("orders") Integer orders,
                                          @RequestHeader String token) {
//        参数校验

        if (name == null || type == null || value == null || total == null || minPrice == null || startTime == null || endTime == null || orders == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
//        身份验证
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Coupon coupon = couponService.addCoupon(name, type, value, total, minPrice, startTime, endTime, orders);
        if (coupon == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "添加优惠券失败");
        }
        return ResultUtils.success(coupon);
    }
}
