package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.Rule;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo {
    private Integer id;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private String name;
    private String desc;

    private List<Rule> rules; // 确保这个字段的类型是 List<Rule>
}
