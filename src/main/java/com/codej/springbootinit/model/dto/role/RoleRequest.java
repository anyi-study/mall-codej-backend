package com.codej.springbootinit.model.dto.role;

import lombok.Data;

@Data
public class RoleRequest {
    private String name;
    private Integer status;
    private String description;
    }
