package com.codej.springbootinit.controller.GoodsComment;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.orders.Orders;
import com.codej.springbootinit.model.vo.GoodsCommentPageVo;
import com.codej.springbootinit.model.vo.GoodsCommentVo;
import com.codej.springbootinit.model.vo.UserPageVo;
import com.codej.springbootinit.service.OrderItemService;
import com.codej.springbootinit.service.OrdersService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/goods_comment")
@Api(tags = "商品评价管理")
public class GoodsCommentController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderItemService orderItemService;
    /**
     * 获取商品评价列表
     * @param token
     * @param page
     * @param limit
     * @param title
     * @return
     */
    @ApiOperation("获取商品评价列表")
    @GetMapping("/{page}")
    public BaseResponse<GoodsCommentPageVo> getGoodsCommentList(@RequestHeader("token") String token,
                                                        @PathVariable("page") int page,
                                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                        @RequestParam(value = "title", required = false) String title) {
        // 参数校验及身份验证
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "token已过期");
        }
        
        GoodsCommentPageVo goodsCommentPage = ordersService.getGoodsCommentList(page, limit, title);
        GoodsCommentVo goodsCommentVo = new GoodsCommentVo();
        
        return ResultUtils.success(goodsCommentPage);
    }

    @PostMapping("/{id}/update_status")
    @ApiOperation("修改商品评价状态")
    public BaseResponse<Boolean> updateGoodsCommentStatus(@RequestHeader("token") String token,
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
        if (ordersService.getById(id) == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id有误，订单不存在");
        }
        Boolean b = ordersService.updateGoodsCommentStatus(status, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新评价失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/review/{id}")
    @ApiOperation("回复商品评价")
    public BaseResponse<Boolean> ReGoodsReview(@RequestHeader("token") String token,
                                                          @RequestParam("data") String data,
                                                          @PathVariable("id") Integer id) {
        //参数校验及身份验证
        if (data == null && id == null) {
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
        if (orderItemService.getById(id) == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id有误，订单不存在");
        }
        Boolean b = orderItemService.ReGoodsReview(data, id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "更新回复失败");
        }
        return ResultUtils.success(true);
    }
}
