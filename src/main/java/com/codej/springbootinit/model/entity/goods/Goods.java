package com.codej.springbootinit.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 分类id
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 商品封面图
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 平均评分
     */
    @TableField(value = "rating")
    private Double rating;

    /**
     * 总销量
     */
    @TableField(value = "sale_count")
    private Integer saleCount;

    /**
     * 评论数
     */
    @TableField(value = "review_count")
    private Integer reviewCount;

    /**
     * 最低sku价格
     */
    @TableField(value = "min_price")
    private BigDecimal minPrice;

    /**
     * 
     */
    @TableField(value = "min_oprice")
    private BigDecimal minOprice;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 库存预警
     */
    @TableField(value = "min_stock")
    private Integer minStock;

    /**
     * 是否审核 0审核中 1通过 2拒绝
     */
    @TableField(value = "ischeck")
    private Integer ischeck;

    /**
     * 状态 0仓库1上架
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 库存显示 0隐藏 1显示
     */
    @TableField(value = "stock_display")
    private Integer stockDisplay;

    /**
     * 运费模板id
     */
    @TableField(value = "express_id")
    private Integer expressId;

    /**
     * sku类型：0单一，1多规格
     */
    @TableField(value = "sku_type")
    private Integer skuType;

    /**
     * 单一规格值
     */
    @TableField(value = "sku_value")
    private String skuValue;

    /**
     * 商品详情
     */
    @TableField(value = "content")
    private String content;

    /**
     * 折扣设置
     */
    @TableField(value = "discount")
    private Double discount;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Integer createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Integer updateTime;

    /**
     * 
     */
    @TableField(value = "delete_time")
    private Integer deleteTime;

    /**
     * 排序
     */
    @TableField(value = "orders")
    private Integer orders;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}