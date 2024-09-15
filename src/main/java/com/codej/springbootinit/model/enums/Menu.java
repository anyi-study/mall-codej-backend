package com.codej.springbootinit.model.enums;// Menu.java
import lombok.Data;

import java.util.List;
@Data
public class Menu {
    private Integer id;
    private Integer ruleId;
    private Integer status;
    private String createTime;
    private String updateTime;
    private String name;
    private String desc;
    private String frontpath;
    private String condition;
    private Integer menu;
    private Integer order;
    private String icon;
    private String method;
    private List<Menu> child; // 子菜单
}