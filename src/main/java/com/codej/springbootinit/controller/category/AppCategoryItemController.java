package com.codej.springbootinit.controller.category;


import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.category.AddShopIdsWithCategoryRequest;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.service.CategoryService;
import com.codej.springbootinit.service.GoodsService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/admin/app_category_item")
@RestController
@Api(tags = "商品分类管理")
public class AppCategoryItemController {
    private final GoodsService goodsService;

    private final CategoryService categoryService;
    public AppCategoryItemController(GoodsService goodsService, CategoryService categoryService) {
        this.goodsService = goodsService;
        this.categoryService = categoryService;
    }

    /**
     * 添加商品关联分类
     * @param addShopIdsWithCategoryRequest
     * @param token
     * @return
     */
    @ApiOperation("添加商品关联分类")
    @PostMapping
    public BaseResponse<String> addShopIdsWithCategory(@RequestBody AddShopIdsWithCategoryRequest addShopIdsWithCategoryRequest,
                                                    @RequestHeader("token") String token) {

        log.info("添加商品关联分类");
        if (addShopIdsWithCategoryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = goodsService.addShopIdsWithCategory(addShopIdsWithCategoryRequest);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "添加商品关联分类失败");
        }
        return ResultUtils.success("ok");
    }
    /**
     * 删除商品分类中的商品关联
     * @param shopId
     * @param token
     * @return
     */
    @ApiOperation("删除商品分类中的商品关联")
    @PostMapping("/{shopId}/delete")
    public BaseResponse<Boolean> deleteCategoryShop(@PathVariable("shopId") Integer shopId,
                                                 @RequestHeader("token") String token) {

        log.info("删除商品分类中的商品关联");
        if (shopId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = goodsService.deleteGoodsWithCategory(shopId);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "删除分类中商品关联失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 获取商品分类的商品关联列表
     * @param categoryId
     * @param token
     * @return
     */
    @ApiOperation("获取商品分类的商品关联列表")
    @GetMapping("/list/{category_id}")
    public BaseResponse<List<Goods>> getCategoryList(@PathVariable("category_id") Integer categoryId,
                                                     @RequestHeader("token") String token) {

        log.info("获取商品分类的商品列表");
        if (categoryId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        List<Goods> goodsList = categoryService.getAppCategoryItemList(categoryId);
        if (goodsList == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "获取分类列表失败");
        }
        return ResultUtils.success(goodsList);
    }
}
