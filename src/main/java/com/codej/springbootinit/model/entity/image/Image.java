package com.codej.springbootinit.model.entity.image;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片表
 * @TableName image
 */
@TableName(value ="image")
@Data
public class Image implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 图片URL
     */
    @TableField(value = "url")
    private String url;

    /**
     * 图片名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 图片路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 创建时间戳，允许空
     */
    @TableField(value = "create_time")
    private Integer createTime;

    /**
     * 更新时间戳，允许空
     */
    @TableField(value = "update_time")
    private Integer updateTime;

    /**
     * 相册ID，允许空
     */
    @TableField(value = "image_class_id")
    private Integer imageClassId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}