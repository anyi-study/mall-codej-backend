package com.codej.springbootinit.model.entity.userLevel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户等级表
 * @TableName user_level
 */
@TableName(value ="user_level")
@Data
public class UserLevel implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 等级名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 等级权重
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 状态：0禁用，1启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 折扣
     */
    @TableField(value = "discount")
    private Integer discount;

    /**
     * 消费满元
     */
    @TableField(value = "max_price")
    private Integer maxPrice;

    /**
     * 消费满次数
     */
    @TableField(value = "max_times")
    private Integer maxTimes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}