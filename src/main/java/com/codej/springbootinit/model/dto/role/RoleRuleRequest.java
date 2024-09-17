package com.codej.springbootinit.model.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleRuleRequest {
    private Integer id;
    private List<Integer> ruleIds;
}
