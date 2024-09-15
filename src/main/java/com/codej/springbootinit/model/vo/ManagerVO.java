package com.codej.springbootinit.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员VO
 */
@Data
public class ManagerVO implements Serializable {
    /**
     * 主键，自增
     */
    private Integer id;

    /**
     * 状态：0禁用，1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 是否是超级管理员：0否，1是
     */
    private Integer isSuperAdmin;
    private static final long serialVersionUID = 1L;
}