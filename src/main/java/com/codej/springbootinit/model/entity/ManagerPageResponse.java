package com.codej.springbootinit.model.entity;
import java.util.List;

public class ManagerPageResponse {

    private List<Manager> list; // 管理员列表
    private long totalCount; // 总记录数
    private List<Role> roles; // 角色列表

    // 默认构造函数
    public ManagerPageResponse() {
    }

    // 带参数构造函数
    public ManagerPageResponse(List<Manager> list, long totalCount, List<Role> roles) {
        this.list = list;
        this.totalCount = totalCount;
        this.roles = roles;
    }

    // Getter 和 Setter 方法
    public List<Manager> getList() {
        return list;
    }

    public void setList(List<Manager> list) {
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "ManagerPageResponse{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                ", roles=" + roles +
                '}';
    }
}
