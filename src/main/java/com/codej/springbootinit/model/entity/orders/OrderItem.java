package com.codej.springbootinit.model.entity.orders;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 订单项表
 * @TableName order_item
 */
@TableName(value ="order_item")
@Data
public class OrderItem implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单ID，允许空
     */
    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 商品ID/商品规则ID，允许空
     */
    @TableField(value = "shop_id")
    private Integer shopId;

    /**
     * 购买数量
     */
    @TableField(value = "num")
    private Integer num;

    /**
     * 购买价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 评分
     */
    @TableField(value = "rating")
    private Integer rating;

    /**
     * 评价
     */
    @TableField(value = "review")
    private String review;

    /**
     * 评价时间
     */
    @TableField(value = "review_time")
    private Integer reviewTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Integer createTime;

    /**
     * 规格类型，默认为1
     */
    @TableField(value = "skus_type")
    private Integer skusType;

    /**
     * 商品ID，允许空
     */
    @TableField(value = "goods_id")
    private Integer goodsId;

    /**
     * 商品数量，默认为0
     */
    @TableField(value = "goods_num")
    private Integer goodsNum;

    /**
     * 用户ID，默认为0
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 客服回复评论
     */
    @TableField(value = "extra")
    private String extra;

    /**
     * 是否可用：1可用，0不可用
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}