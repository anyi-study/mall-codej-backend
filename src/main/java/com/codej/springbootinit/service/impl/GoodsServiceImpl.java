package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.GoodsMapper;
import com.codej.springbootinit.model.dto.category.AddShopIdsWithCategoryRequest;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 10306
 * @description 针对表【goods】的数据库操作Service实现
 * @createDate 2024-09-18 14:29:40
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {
    private final GoodsMapper goodsMapper;

    public GoodsServiceImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public Boolean deleteGoodsWithCategory(Integer shopId) {
        if (shopId == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        Goods goods = goodsMapper.selectById(shopId);
        if (goods == null) {
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        goods.setCategoryId(0);
        int i = goodsMapper.updateById(goods);
        return i > 0;
    }

    /**
     * 添加商品关联分类
     *
     * @param addShopIdsWithCategoryRequest
     * @return
     */
    @Override
    public Boolean addShopIdsWithCategory(AddShopIdsWithCategoryRequest addShopIdsWithCategoryRequest) {
        // 校验请求参数是否为空
        if (addShopIdsWithCategoryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }

        List<Integer> goodsIds = addShopIdsWithCategoryRequest.getGoodsIds();
        if (goodsIds.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品ID列表不能为空");
        }

        // 批量获取商品信息
        List<Goods> goodsList = goodsMapper.selectBatchIds(goodsIds);
        if (goodsList.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未找到商品");
        }

        Map<Integer, Goods> goodsMap = goodsList.stream()
                .collect(Collectors.toMap(Goods::getId, goods -> goods));

        Integer categoryId = addShopIdsWithCategoryRequest.getCategoryId();
        for (Integer goodsId : goodsIds) {
            Goods goods = goodsMap.get(goodsId);
            if (goods == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "未找到商品ID: " + goodsId);
            }
            goods.setCategoryId(categoryId);
        }

        // 批量更新商品类别
        int updatedRows = goodsMapper.updateBatchById(categoryId, goodsList);
        if (updatedRows < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品关联分类失败");
        }

        return true;
    }

}




