package com.codej.springbootinit.service;

import com.codej.springbootinit.model.entity.orders.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 10306
* @description 针对表【order_item(订单项表)】的数据库操作Service
* @createDate 2024-09-19 12:36:38
*/
public interface OrderItemService extends IService<OrderItem> {

    /**
     * 修改商品评价
     * @param data
     * @param id
     * @return
     */
    Boolean ReGoodsReview(String data, Integer id);
}
