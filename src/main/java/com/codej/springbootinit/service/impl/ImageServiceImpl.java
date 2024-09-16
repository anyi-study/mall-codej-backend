package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.ImageClassMapper;
import com.codej.springbootinit.mapper.ImageMapper;
import com.codej.springbootinit.model.dto.admin_image.ImageClassRequest;
import com.codej.springbootinit.model.dto.admin_image.ImageCountDTO;
import com.codej.springbootinit.model.entity.image.Image;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.model.vo.ImageResponse;
import com.codej.springbootinit.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 10306
* @description 针对表【image(图片表)】的数据库操作Service实现
* @createDate 2024-09-16 00:01:46
*/
@Service
@Slf4j
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

    @Override
    public List<ImageResponse> uploadImages(Integer imageClassId, MultipartFile[] files) {
        List<ImageResponse> uploadedImages = new ArrayList<>();

        // 获取当前的 Unix 时间戳（秒）
        int currentTime = (int) (System.currentTimeMillis() / 1000);

        // 遍历上传的文件
        for (MultipartFile file : files) {
            // 保存文件到服务器
            String fileName = saveFileToServer(file);

            // 创建图片记录
            Image image = new Image();
            image.setName(fileName);
            image.setImageClassId(imageClassId);
            image.setPath("/upload/" + fileName); // 假设图片保存路径
            image.setCreateTime(currentTime);     // 使用 Unix 时间戳表示创建时间
            image.setUpdateTime(currentTime);     // 使用 Unix 时间戳表示更新时间

            // 保存图片信息到数据库
            imageMapper.insert(image);
            // 将 Unix 时间戳转换为 LocalDateTime
            LocalDateTime createTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTime), ZoneId.systemDefault());
            LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTime), ZoneId.systemDefault());

            // 构造响应数据
            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setId(image.getId());
            imageResponse.setUrl("/upload/" + fileName);  // 假设URL为此路径
            imageResponse.setName(fileName);
            imageResponse.setPath(image.getPath());
            imageResponse.setImageClassId(image.getImageClassId());
            imageResponse.setCreateTime(createTime);     // 使用 LocalDateTime
            imageResponse.setUpdateTime(updateTime);     // 使用 LocalDateTime

            uploadedImages.add(imageResponse);
        }

        return uploadedImages;
    }

    /**
     * 删除图片
     * @param ids
     * @return
     */
    @Override
    public boolean deleteImages(List<Integer> ids) {
        try {
            // 获取图片记录
            List<Image> images = imageMapper.selectBatchIds(ids);
            if (images.isEmpty()) {
                return false;
            }

            // 删除数据库记录
            imageMapper.deleteBatchIds(ids);

            // 删除文件系统中的图片
            for (Image image : images) {
                File file = new File(System.getProperty("user.dir") + "/uploads/" + image.getName());
                if (file.exists()) {
                    if (!file.delete()) {
                        // 日志记录删除失败
                        log.warn("Failed to delete file: {}", file.getAbsolutePath());
                    }
                }
            }

            return true;
        } catch (Exception e) {
            log.error("Error deleting images: ", e);
            return false;
        }
    }

    @Override
    public boolean updateImageName(Integer imageId, String name) {
        int rowsAffected = imageMapper.updateImageName(imageId, name);
        return rowsAffected > 0;
    }

    // 文件保存方法
    private String saveFileToServer(MultipartFile file) {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 确定文件存储路径
//            String uploadPath = "C:/uploads";
            String uploadPath = System.getProperty("user.dir") + "/uploads";  // 使用项目根目录下的 uploads 文件夹

            // 日志打印文件信息
            log.info("正在保存文件: {}", fileName);
            log.info("文件大小: {}", file.getSize());
            log.info("文件类型: {}", file.getContentType());

            // 确保文件路径存在
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建目录失败");
                }
            }

            // 保存文件
            File serverFile = new File(uploadPath + File.separator + fileName);
            file.transferTo(serverFile);

            // 返回文件路径
            return fileName;
        } catch (IOException e) {
            log.error("文件保存失败: ", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件上传失败");  // 抛出自定义异常
        }
    }

}




