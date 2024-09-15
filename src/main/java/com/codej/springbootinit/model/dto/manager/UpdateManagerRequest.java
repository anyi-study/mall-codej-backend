package com.codej.springbootinit.model.dto.manager;

import lombok.Data;
@Data
public class UpdateManagerRequest {
    private String username;
    private String password;
    private Integer roleId;
    private Integer status;
    private String avatar;
}