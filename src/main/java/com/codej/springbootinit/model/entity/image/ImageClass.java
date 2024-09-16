package com.codej.springbootinit.model.entity.image;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 相册表
 * @TableName image_class
 */
@TableName(value ="image_class")
@Data
public class ImageClass implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 相册名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 排序，默认为50
     */
    @TableField(value = "orders")
    private Integer orders;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}