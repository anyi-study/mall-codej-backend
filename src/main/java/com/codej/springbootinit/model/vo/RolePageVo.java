package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.Rule;
import lombok.Data;

import java.util.List;

@Data
public class RolePageVo {

    private List<Role> list;  // 管理员列表
    private long totalCount;     // 总记录数
    private List<Rule> roles;    // 角色列表

    public RolePageVo() {}

    public RolePageVo(List<Role> list, long totalCount, List<Rule> roles) {
        this.list = list;
        this.totalCount = totalCount;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ManagerPageVo{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                ", roles=" + roles +
                '}';
    }
}
