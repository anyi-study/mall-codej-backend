package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import lombok.Data;

import java.util.List;
@Data
public class ManagerPageVo {

    private List<Manager> list;  // 管理员列表
    private long totalCount;     // 总记录数
    private List<Role> roles;    // 角色列表

    public ManagerPageVo() {}

    public ManagerPageVo(List<Manager> list, long totalCount, List<Role> roles) {
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
