package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.mapper.ImageClassMapper;
import com.codej.springbootinit.mapper.ImageMapper;
import com.codej.springbootinit.model.dto.admin_image.ImageClassRequest;
import com.codej.springbootinit.model.dto.admin_image.ImageCountDTO;
import com.codej.springbootinit.model.entity.image.Image;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 10306
* @description 针对表【image(图片表)】的数据库操作Service实现
* @createDate 2024-09-16 00:01:46
*/
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
    implements ImageService {
    @Autowired
    private ImageClassMapper imageClassMapper;

    @Autowired
    private ImageMapper imageMapper; // 需要添加对 image 表的 Mapper 进行注入
    @Override
    public PageResponse<ImageClassRequest> getImageList(Integer page, Integer limit) {
        // 设置分页参数
        Page<ImageClass> pageParam = new Page<>(page, limit);

        // 执行分页查询
        IPage<ImageClass> imageClassPage = imageClassMapper.selectPage(pageParam, null);

        // 统计每个分类的图片数量
        List<Long> imageClassIds = imageClassPage.getRecords().stream()
                .map(ImageClass::getId)
                .collect(Collectors.toList());

        // 查询图片数量
        Map<Long, Integer> imageCountMap;
        if (!imageClassIds.isEmpty()) {
            List<ImageCountDTO> imageCountList = imageMapper.countImagesByClassIds(imageClassIds);
            imageCountMap = imageCountList.stream()
                    .collect(Collectors.toMap(ImageCountDTO::getImageClassId, ImageCountDTO::getCount));
        } else {
            imageCountMap = new HashMap<>();
        }

        // 构造响应数据
        List<ImageClassRequest> imageClassDTOList = imageClassPage.getRecords().stream().map(item -> {
            ImageClassRequest dto = new ImageClassRequest();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setOrder(item.getOrders());
            dto.setImagesCount(imageCountMap.getOrDefault(item.getId(), 0)); // 设置图片数量
            return dto;
        }).collect(Collectors.toList());

        // 构造分页响应数据
        PageResponse<ImageClassRequest> pageResponse = new PageResponse<>(imageClassDTOList, imageClassPage.getTotal());

        return pageResponse;
    }

    /**
     * 根据分类id获取图片列表
     * @param imageClassId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageResponse<Image> getImagesByClassId(Long imageClassId, Integer page, Integer limit) {
        // 设置分页参数
        Page<Image> pageParam = new Page<>(page, limit);

        // 执行分页查询
        IPage<Image> imagePage = imageMapper.selectImagesByClassId(pageParam, imageClassId);

        // 构造分页响应数据
        return new PageResponse<>(imagePage.getRecords(), imagePage.getTotal());
    }


}




