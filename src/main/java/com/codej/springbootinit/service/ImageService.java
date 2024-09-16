package com.codej.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.model.dto.admin_image.ImageClassRequest;
import com.codej.springbootinit.model.entity.image.Image;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.model.vo.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 10306
* @description 针对表【image(图片表)】的数据库操作Service
* @createDate 2024-09-16 00:01:46
*/
public interface ImageService extends IService<Image> {

    /**
     * 获取图片列表
     * @param page
     * @param limit
     * @return
     */
    PageResponse<ImageClassRequest> getImageList(Integer page, Integer limit);

    /**
     * 获取指定分类下的图片列表
     * @param imageClassId
     * @param page
     * @param limit
     * @return
     */
    PageResponse<Image> getImagesByClassId(Long imageClassId, Integer page, Integer limit);

    /**
     * 上传图片
     * @param imageClassId
     * @param files
     * @return
     */

    List<ImageResponse> uploadImages(Integer imageClassId, MultipartFile[] files);

    /**
     * 删除图片
     * @param ids
     * @return
     */
    boolean deleteImages(List<Integer> ids);

    /**
     * 修改图片名称
     * @param imageId
     * @param name
     * @return
     */
    boolean updateImageName(Integer imageId, String name);
}
