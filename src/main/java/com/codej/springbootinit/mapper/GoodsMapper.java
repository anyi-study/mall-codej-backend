package com.codej.springbootinit.mapper;

import com.codej.springbootinit.model.entity.goods.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 10306
* @description 针对表【goods】的数据库操作Mapper
* @createDate 2024-09-18 14:29:40
* @Entity com.codej.springbootinit.model.entity.goods.Goods
*/
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 批量修改商品库存
     * @param goodsList
     * @return
     */
    // 批量更新商品类别
    int updateBatchById(@Param("category_id") Integer categoryId, @Param("list") List<Goods> goodsList);
}




