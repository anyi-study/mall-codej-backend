package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import lombok.Data;

import java.util.List;

@Data
public class UserLevelPageVo {

    private List<UserLevel> list;  // 管理员列表
    private long totalCount;     // 总记录数

    public UserLevelPageVo() {}

    public UserLevelPageVo(List<UserLevel> list, long totalCount) {
        this.list = list;
        this.totalCount = list.size();
    }


    @Override
    public String toString() {
        return "UserPageVo{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                '}';
    }
}
