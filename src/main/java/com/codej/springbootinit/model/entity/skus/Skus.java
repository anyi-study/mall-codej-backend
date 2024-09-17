package com.codej.springbootinit.model.entity.skus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 规格表
 * @TableName skus
 */
@TableName(value ="skus")
@Data
public class Skus implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规格分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 规格类型：0无，1颜色，2图片
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 状态：0禁用，1启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序，默认为50
     */
    @TableField(value = "orders")
    private Integer orders;

    /**
     * 默认值
     */
    @TableField(value = "defaults")
    private String defaults;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}