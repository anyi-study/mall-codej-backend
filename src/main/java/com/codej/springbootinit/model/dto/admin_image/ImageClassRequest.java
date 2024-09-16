package com.codej.springbootinit.model.dto.admin_image;

import lombok.Data;

@Data
public class ImageClassRequest {
    private Long id;
    private String name;
    private Integer order;
    private Integer imagesCount;
}
