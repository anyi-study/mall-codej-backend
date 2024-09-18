package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.goods.Goods;
import lombok.Data;

import java.util.List;

@Data
public class CategoryVo {

    private Long id;
    private String name;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private Long categoryId;
    private Integer orders;

    private List<Goods> child;
}