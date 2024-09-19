package com.codej.springbootinit.service;

import com.codej.springbootinit.model.entity.orders.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.vo.GoodsCommentPageVo;

/**
* @author 10306
* @description 针对表【orders】的数据库操作Service
* @createDate 2024-09-19 12:18:21
*/
public interface OrdersService extends IService<Orders> {

    /**
     * 获取商品评论列表
     *
     * @param page
     * @param limit
     * @param title
     * @return
     */
    GoodsCommentPageVo getGoodsCommentList(int page, int limit, String title);

    /**
     * 修改商品评论状态
     * @param status
     * @param id
     * @return
     */
    Boolean updateGoodsCommentStatus(Integer status, Integer id);
}
