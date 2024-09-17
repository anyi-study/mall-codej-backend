package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.skus.Skus;
import lombok.Data;

import java.util.List;

@Data
public class SkusPageVo {

    private List<Skus> list;  // 管理员列表
    private long totalCount;     // 总记录数

    public SkusPageVo() {}

    public SkusPageVo(List<Skus> list, long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ManagerPageVo{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                '}';
    }
}
