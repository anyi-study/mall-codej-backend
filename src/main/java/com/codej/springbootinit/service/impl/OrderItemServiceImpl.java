package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.orders.OrderItem;
import com.codej.springbootinit.service.OrderItemService;
import com.codej.springbootinit.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【order_item(订单项表)】的数据库操作Service实现
* @createDate 2024-09-19 12:36:38
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{
@Autowired
private OrderItemMapper orderItemMapper;
    /**
     * 修改商品评价
     * @param data
     * @param id
     * @return
     */
    @Override
    public Boolean ReGoodsReview(String data, Integer id) {
        if (data == null && id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.ID_ERROR);
        }
        OrderItem orderItem = orderItemMapper.selectById(id);
        if (orderItem == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        orderItem.setReview(data);
        return orderItemMapper.updateById(orderItem) > 0;
    }
}




