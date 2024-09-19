package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.Rule;
import lombok.Data;

import java.util.List;

@Data
public class GoodsCommentPageVo {

    private List<GoodsCommentVo> list;  // 管理员列表
    private long totalCount;     // 总记录数

    public GoodsCommentPageVo() {}

    public GoodsCommentPageVo(List<GoodsCommentVo> list, long totalCount) {
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
