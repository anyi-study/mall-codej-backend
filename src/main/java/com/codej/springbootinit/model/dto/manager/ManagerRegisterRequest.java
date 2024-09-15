package com.codej.springbootinit.model.dto.manager;

import lombok.Data;

@Data
public class ManagerRegisterRequest {
    private String username;
    private String password;
    private String checkPassword;

}
