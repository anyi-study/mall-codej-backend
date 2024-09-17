package com.codej.springbootinit.model.dto.coupon;

import lombok.Data;

import java.util.Date;
@Data
public class CouponRequest {
    // 优惠券名称
    private String name;
    // 类型：0满减,1折扣
    private int type;
    // 面值
    private double value;
    // 发行量
    private int total;
    // 最低使用价格
    private double minPrice;
    // 开始时间
    private Date startTime;
    // 结束时间
    private Date endTime;
    // 排序
    private int order;
}
