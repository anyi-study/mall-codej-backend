package com.codej.springbootinit.model.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 分类表
 * @TableName category
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称，允许空
     */
    @TableField(value = "name")
    private String name;

    /**
     * 状态，默认为1
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
     * 父级分类ID，默认为0
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 排序，默认为50
     */
    @TableField(value = "orders")
    private Integer orders;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}