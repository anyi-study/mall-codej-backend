package com.codej.springbootinit.model.dto.admin_image;

public class ImageCountDTO {
    private Long imageClassId;
    private Integer count;

    // Getters and setters
    public Long getImageClassId() {
        return imageClassId;
    }

    public void setImageClassId(Long imageClassId) {
        this.imageClassId = imageClassId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
