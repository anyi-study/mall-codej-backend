package com.codej.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.entity.category.Category;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.model.vo.AddCategoryVo;
import com.codej.springbootinit.model.vo.CategoryVo;

import java.util.List;

/**
* @author 10306
* @description 针对表【category(分类表)】的数据库操作Service
* @createDate 2024-09-18 14:17:31
*/
public interface CategoryService extends IService<Category> {

    /**
     * 获取商品分类列表
     * @return
     */
    List<CategoryVo> getCategoryList();

    /**
     * 获取添加商品分类列表
     * @param name
     * @return
     */
    AddCategoryVo addCategory(String name);

    /**
     * 修改商品分类
     * @param id
     * @param name
     * @return
     */
    Boolean updateCategory(Integer id, String name);

    /**
     * 修改商品分类状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateCategoryStatus(Integer id, Integer status);

    /**
     * 删除商品分类
     * @param id
     * @return
     */
    Boolean deleteCategory(Integer id);

    /**
     * 获取商品分类的商品列表
     * @return
     */
    List<Goods> getAppCategoryItemList(Integer categoryId);
}
