package com.codej.springbootinit.model.dto.skus;

import lombok.Data;

@Data
public class SkusRequest {
    private Integer status;
    private String name;
    private Integer orders;
    private String defaults;
}
