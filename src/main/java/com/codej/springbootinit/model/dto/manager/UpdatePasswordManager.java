package com.codej.springbootinit.model.dto.manager;

import lombok.Data;

@Data
public class UpdatePasswordManager {
    private String oldpassword;
    private String password;
    private String repassword;
}
