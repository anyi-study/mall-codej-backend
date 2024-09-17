package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.coupon.Coupon;
import lombok.Data;

import java.util.List;

@Data
public class CouponPageVo {

    private List<Coupon> list;  // 管理员列表
    private long totalCount;     // 总记录数

    public CouponPageVo() {}

    public CouponPageVo(List<Coupon> list, long totalCount) {
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
