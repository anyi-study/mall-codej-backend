package com.codej.springbootinit.controller.category;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.vo.AddCategoryVo;
import com.codej.springbootinit.model.vo.CategoryVo;
import com.codej.springbootinit.service.CategoryService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/admin/category")
@RestController
@Api(tags = "商品分类管理")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    /**
     * 删除商品分类
     *
     * @param token
     * @return
     */
    @ApiOperation("删除商品分类的关联商品")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteCategory(@RequestHeader("token") String token,
                                                      @PathVariable("id") Integer id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id参数错误");
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = categoryService.deleteCategory(id);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "删除商品分类失败");
        }
        return ResultUtils.success(true);
    }
    /**
     * 修改商品分类状态
     *
     * @param token
     * @param status
     * @return
     */
    @ApiOperation("修改商品分类状态")
    @PostMapping("/{id}/update_status")
    public BaseResponse<Boolean> updateCategoryStatus(@RequestHeader("token") String token,
                                                @PathVariable("id") Integer id,
                                                @RequestParam Integer status) {
        if (id == null || status == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id参数错误");
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = categoryService.updateCategoryStatus(id,status);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "修改状态失败");
        }
        return ResultUtils.success(true);
    }
    /**
     * 修改商品分类
     *
     * @param token
     * @param name
     * @return
     */
    @ApiOperation("修改商品分类")
    @PostMapping("/{id}")
    public BaseResponse<Boolean> updateCategory(@RequestHeader("token") String token,
                                                @PathVariable("id") Integer id,
                                                @RequestParam String name) {
        if (id == null ||name == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id参数错误");
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        Boolean b = categoryService.updateCategory(id,name);
        if (!b) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "修改失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 添加商品分类
     *
     * @param token
     * @param name
     * @return
     */
    @ApiOperation("添加商品分类")
    @PostMapping
    public BaseResponse<AddCategoryVo> addCategory(@RequestHeader("token") String token,
                                                   @RequestParam String name) {
        if (name == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        AddCategoryVo addCategory = categoryService.addCategory(name);
        if (addCategory == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "添加商品分类失败");
        }
        return ResultUtils.success(addCategory);
    }

    /**
     * 获取商品分类列表
     *
     * @param token
     * @return
     */
    @ApiOperation("获取商品分类列表")
    @GetMapping
    public BaseResponse<List<CategoryVo>> getCategoryList(@RequestHeader("token") String token) {

        // 验证token的逻辑可以在此处理
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        List<CategoryVo> categoryList = categoryService.getCategoryList();
        if (categoryList == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "获取分类列表失败");
        }
        return ResultUtils.success(categoryList);
    }

}
