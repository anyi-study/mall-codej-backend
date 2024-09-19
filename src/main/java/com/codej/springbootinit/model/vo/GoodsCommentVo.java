package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.goods.Goods;
import com.codej.springbootinit.model.entity.user.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoodsCommentVo {
    private Integer id;
    private Integer orderId;
    private Integer shopId;
    private Integer num;
    private BigDecimal price;
    private Integer rating;
    private String review;
    private List<String> reviewImage;
    private LocalDateTime reviewTime;
    private LocalDateTime createTime;
    private Integer skusType;
    private Integer goodsId;
    private Integer goodsNum;
    private Integer userId;
    private Integer status;
    private Object extra;
    private Goods goods;
    private User user;
}
