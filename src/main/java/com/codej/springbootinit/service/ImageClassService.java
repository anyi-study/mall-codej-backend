package com.codej.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.entity.image.ImageClass;

/**
* @author 10306
* @description 针对表【image_class(相册表)】的数据库操作Service
* @createDate 2024-09-16 00:01:49
*/
public interface ImageClassService extends IService<ImageClass> {

    /**
     * 增加图库分类
     * @param name
     * @param order
     * @return
     */
    ImageClass addImageClass(String name, Integer order);

    /**
     * 修改图库分类
     * @param id
     * @param name
     * @param order
     * @return
     */
    boolean updateImageClass(Long id, String name, Integer order);

    /**
     * 删除图库分类
     * @param id
     * @return
     */
    boolean deleteImageClass(Long id);
}
