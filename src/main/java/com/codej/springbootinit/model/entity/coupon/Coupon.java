package com.codej.springbootinit.model.entity.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 优惠券表
 * @TableName coupon
 */
@TableName(value ="coupon")
@Data
public class Coupon implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 优惠券名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型：固定金额/百分比折扣  0固定金额 1百分比
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 折扣值，根据不同类型含义不同
     */
    @TableField(value = "value")
    private BigDecimal value;

    /**
     * 总数
     */
    @TableField(value = "total")
    private Integer total;

    /**
     * 已使用
     */
    @TableField(value = "used")
    private Integer used;

    /**
     * 最低价格
     */
    @TableField(value = "min_price")
    private BigDecimal minPrice;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Integer startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Integer endTime;

    /**
     * 优惠券是否生效 0不生效 1生效
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间戳，允许空
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 更新时间戳，允许空
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 排序，默认为50
     */
    @TableField(value = "orders")
    private Integer orders;

    /**
     * 备注
     */
    @TableField(value = "description")
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}