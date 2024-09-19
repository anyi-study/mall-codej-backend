package com.codej.springbootinit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.GoodsMapper;
import com.codej.springbootinit.mapper.OrderItemMapper;
import com.codej.springbootinit.mapper.OrdersMapper;
import com.codej.springbootinit.mapper.UserMapper;
import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.model.entity.orders.OrderItem;
import com.codej.springbootinit.model.entity.orders.Orders;
import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.vo.GoodsCommentPageVo;
import com.codej.springbootinit.model.vo.GoodsCommentVo;
import com.codej.springbootinit.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 10306
 * @description 针对表【orders】的数据库操作Service实现
 * @createDate 2024-09-19 12:18:21
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取商品评论列表
     *
     * @param page
     * @param limit
     * @param title
     * @return
     */
    @Override
    public GoodsCommentPageVo getGoodsCommentList(int page, int limit, String title) {
        // 设置分页条件
        Page<Orders> pageCondition = new Page<>(page, limit);

        // 构建查询条件
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();

        // 执行分页查询
        IPage<Orders> ordersIPage = this.page(pageCondition, queryWrapper);
        // 构造返回数据
        List<Orders> ordersList = ordersIPage.getRecords();
        long totalCount = ordersIPage.getTotal();
        GoodsCommentVo goodsCommentVo = new GoodsCommentVo();
        // 创建商品id list
        ArrayList<Integer> shopIds = new ArrayList<>();
        List<GoodsCommentVo> list = new ArrayList<>();
        // 遍历订单列表，获取每个订单的ID
        for (Orders order : ordersList) {
            System.out.println("----goodsCommentVo------"+goodsCommentVo);
            System.out.println("----order------"+order);

            // 使用订单ID查询对应的OrderItem列表
            List<OrderItem> orderItems = orderItemMapper.selectList(
                    new QueryWrapper<OrderItem>().eq("order_id", order.getId())
            );
            list.add(goodsCommentVo);
            // 从每个OrderItem中提取商品ID并添加到shopIds中
            for (OrderItem item : orderItems) {
                goodsCommentVo.setGoodsId(item.getGoodsId());
                Goods goods = goodsMapper.selectById(item.getGoodsId());
                goodsCommentVo.setGoods(goods);
                User user = userMapper.selectById(item.getUserId());
                goodsCommentVo.setUser(user);
                BeanUtil.copyProperties(item, goodsCommentVo);
                list.add(goodsCommentVo);
            }
        }
        GoodsCommentPageVo goodsCommentPageVo = new GoodsCommentPageVo();
        goodsCommentPageVo.setList(list);
        goodsCommentPageVo.setTotalCount(totalCount);

        // 如果提供了商品标题，按标题过滤
        if (title != null && !title.isEmpty()) {
            queryWrapper.like("title", title);
        }

        return goodsCommentPageVo;
    }

    @Override
    public Boolean updateGoodsCommentStatus(Integer status, Integer id) {
        if (status == null && id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        OrderItem orderItem = orderItemMapper.selectById(id);
        orderItem.setStatus(status);
        return orderItemMapper.updateById(orderItem) >= 1;
    }
}




