package com.codej.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 规则表
 * @TableName rule
 */
@TableName(value ="rule")
@Data
public class Rule implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级ID，默认为0
     */
    @TableField(value = "rule_id")
    private Integer ruleId;

    /**
     * 状态：0关闭，1启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Integer createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Integer updateTime;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 前端路由name值
     */
    @TableField(value = "description")
    private String description;

    /**
     * 前台路由注册路径
     */
    @TableField(value = "frontpath")
    private String frontpath;

    /**
     * 条件
     */
    @TableField(value = "condition")
    private String condition;

    /**
     * 是否显示为菜单：0不显示，1显示
     */
    @TableField(value = "menu")
    private Integer menu;

    /**
     * 排序，默认为50
     */
    @TableField(value = "order")
    private Integer order;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 请求类型，默认为GET
     */
    @TableField(value = "method")
    private String method;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}