package com.codej.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色规则关联表
 * @TableName role_rule
 */
@TableName(value ="role_rule")
@Data
public class RoleRule implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色ID，允许空
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 规则ID，允许空
     */
    @TableField(value = "rule_id")
    private Integer ruleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}