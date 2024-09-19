package com.codej.springbootinit.model.entity.orders;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单唯一流水号
     */
    @TableField(value = "no")
    private String no;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 收货地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 订单总价格
     */
    @TableField(value = "total_price")
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 支付时间
     */
    @TableField(value = "paid_time")
    private Integer paidTime;

    /**
     * 支付方式
     */
    @TableField(value = "payment_method")
    private String paymentMethod;

    /**
     * 支付平台订单号
     */
    @TableField(value = "payment_no")
    private String paymentNo;

    /**
     * 退款状态
     */
    @TableField(value = "refund_status")
    private String refundStatus;

    /**
     * 退款单号
     */
    @TableField(value = "refund_no")
    private String refundNo;

    /**
     * 是否关闭
     */
    @TableField(value = "closed")
    private Integer closed;

    /**
     * 物流状态
     */
    @TableField(value = "ship_status")
    private String shipStatus;

    /**
     * 物流数据
     */
    @TableField(value = "ship_data")
    private String shipData;

    /**
     * 额外数据
     */
    @TableField(value = "extra")
    private String extra;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Integer createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Integer updateTime;

    /**
     * 是否已评价
     */
    @TableField(value = "reviewed")
    private Integer reviewed;

    /**
     * 使用优惠券id
     */
    @TableField(value = "coupon_user_id")
    private Integer couponUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}