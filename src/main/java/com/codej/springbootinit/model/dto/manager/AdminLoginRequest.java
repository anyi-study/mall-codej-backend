package com.codej.springbootinit.model.dto.manager;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String username;
    private String password;
}
