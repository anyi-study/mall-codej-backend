package com.codej.springbootinit.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户等级ID
     */
    @TableField(value = "user_level_id")
    private Integer userLevelId;

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
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Integer lastLoginTime;

    /**
     * 状态：0禁用，1启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 微信openid
     */
    @TableField(value = "wechat_openid")
    private String wechatOpenid;

    /**
     * 推广用户数量
     */
    @TableField(value = "share_num")
    private Integer shareNum;

    /**
     * 推广订单数量
     */
    @TableField(value = "share_order_num")
    private Integer shareOrderNum;

    /**
     * 订单金额
     */
    @TableField(value = "order_price")
    private BigDecimal orderPrice;

    /**
     * 账户佣金
     */
    @TableField(value = "commission")
    private BigDecimal commission;

    /**
     * 已提现金额
     */
    @TableField(value = "cash_out_price")
    private BigDecimal cashOutPrice;

    /**
     * 已提现次数
     */
    @TableField(value = "cash_out_time")
    private Integer cashOutTime;

    /**
     * 未提现金额
     */
    @TableField(value = "no_cash_out_price")
    private BigDecimal noCashOutPrice;

    /**
     * 一级推广人ID
     */
    @TableField(value = "p1")
    private Integer p1;

    /**
     * 二级推广人ID
     */
    @TableField(value = "p2")
    private Integer p2;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}