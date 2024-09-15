package com.codej.springbootinit.model.vo;// UserPermissionsResponse.java
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.enums.Menu;
import lombok.Data;

import java.util.List;
@Data
public class UserPermissionsResponse {
    private Integer id;
    private String username;
    private String avatar;
    private Integer isSuperAdmin;
    private Role role;
    private List<Menu> menus;
    private List<String> ruleNames;
}