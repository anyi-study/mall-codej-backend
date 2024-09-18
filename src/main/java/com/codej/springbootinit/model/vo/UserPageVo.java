package com.codej.springbootinit.model.vo;

import com.codej.springbootinit.model.entity.user.User;
import com.codej.springbootinit.model.entity.userLevel.UserLevel;
import lombok.Data;

import java.util.List;

@Data
public class UserPageVo {

    private List<User> list;  // 管理员列表
    private long totalCount;     // 总记录数
    private List<UserLevel> user_level;

    public UserPageVo() {}

    public UserPageVo(List<User> list, long totalCount, List<UserLevel> user_level) {
        this.list = list;
        this.totalCount = totalCount;
        this.user_level = user_level;
    }

    @Override
    public String toString() {
        return "UserPageVo{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                ", user_level=" + user_level +
                '}';
    }
}
