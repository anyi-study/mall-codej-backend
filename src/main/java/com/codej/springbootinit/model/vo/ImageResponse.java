package com.codej.springbootinit.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ImageResponse {
    private Integer id;
    private String url;
    private String name;
    private String path;
    private Integer imageClassId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // Getters and setters
}
