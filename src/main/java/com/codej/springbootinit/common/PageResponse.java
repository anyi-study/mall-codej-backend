package com.codej.springbootinit.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> list; // 泛型列表，用于不同的实体类
    private long totalCount; // 总记录数

    public PageResponse() {}

    public PageResponse(List<T> list, long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "list=" + list +
                ", totalCount=" + totalCount +
                '}';
    }
}

