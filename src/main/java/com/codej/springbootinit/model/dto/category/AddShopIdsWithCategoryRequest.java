package com.codej.springbootinit.model.dto.category;

import lombok.Data;

import java.util.List;

@Data
public class AddShopIdsWithCategoryRequest {
    private List<Integer> goodsIds;
    private Integer categoryId;
}
