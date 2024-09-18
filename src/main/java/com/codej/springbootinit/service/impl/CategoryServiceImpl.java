package com.codej.springbootinit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.GoodsMapper;
import com.codej.springbootinit.model.entity.category.Category;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.model.vo.AddCategoryVo;
import com.codej.springbootinit.model.vo.CategoryVo;
import com.codej.springbootinit.service.CategoryService;
import com.codej.springbootinit.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author 10306
* @description 针对表【category(分类表)】的数据库操作Service实现
* @createDate 2024-09-18 14:17:31
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final GoodsMapper goodsMapper;

    // 构造器注入
    public CategoryServiceImpl(CategoryMapper categoryMapper, GoodsMapper goodsMapper) {
        this.categoryMapper = categoryMapper;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<CategoryVo> getCategoryList() {
        // 查询所有分类
        List<Category> categories = categoryMapper.selectList(null);

        // 初始化返回的 CategoryVo 列表
        List<CategoryVo> categoryVos = new ArrayList<>();

        // 遍历所有分类
        for (Category category : categories) {
            // 创建一个 CategoryVo 实例
            CategoryVo categoryVo = new CategoryVo();

            // 根据 categoryId 查询子分类或商品
            List<Goods> childGoods = goodsMapper.selectList(
                    new QueryWrapper<Goods>().eq("category_id", category.getId())
            );
            categoryVo.setChild(childGoods);

            // 复制属性到 CategoryVo
            BeanUtil.copyProperties(category, categoryVo);

            // 将 categoryVo 添加到返回列表
            categoryVos.add(categoryVo);
        }

        return categoryVos;
    }

    /**
     * 添加分类
     * @param name
     * @return
     */
    @Override
    public AddCategoryVo addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setCreateTime(System.currentTimeMillis());
        category.setUpdateTime(System.currentTimeMillis());

        int insert = categoryMapper.insert(category);
        if (insert <= 0){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"添加分类失败");
        }

        AddCategoryVo addCategoryVo = new AddCategoryVo();
        BeanUtil.copyProperties(category, addCategoryVo);
        return addCategoryVo;
    }

    /**
     * 修改商品分类
     * @param id
     * @param name
     * @return
     */
    @Override
    public Boolean updateCategory(Integer id, String name) {
//        查询id是否存在
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        category.setName(name);
        int i = categoryMapper.updateById(category);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改商品分类状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean updateCategoryStatus(Integer id, Integer status) {
        //        查询id是否存在
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        category.setStatus(status);
        int i = categoryMapper.updateById(category);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除商品分类
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCategory(Integer id) {
        //        查询id是否存在
        if (id == null|| id <= 0){
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        int i = categoryMapper.deleteById(id);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取商品分类的商品列表
     * @return
     */
    @Override
    public List<Goods> getAppCategoryItemList(Integer categoryId) {
        if (categoryId <= 0){
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        return goodsMapper.selectList(new QueryWrapper<Goods>().eq("category_id", categoryId));
    }
}





