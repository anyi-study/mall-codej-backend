package com.codej.springbootinit.service;

import com.codej.springbootinit.model.dto.category.AddShopIdsWithCategoryRequest;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 10306
* @description 针对表【goods】的数据库操作Service
* @createDate 2024-09-18 14:29:40
*/
public interface GoodsService extends IService<Goods> {

    /**
     * 删除商品关联
     * @param shopId
     * @return
     */
    Boolean deleteGoodsWithCategory(Integer shopId);

    /**
     * 添加商品关联
     * @param addShopIdsWithCategoryRequest
     * @return
     */
    Boolean addShopIdsWithCategory(AddShopIdsWithCategoryRequest addShopIdsWithCategoryRequest);
}
