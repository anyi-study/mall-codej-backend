package com.codej.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codej.springbootinit.model.dto.admin_image.ImageCountDTO;
import com.codej.springbootinit.model.entity.image.Image;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author 10306
* @description 针对表【image(图片表)】的数据库操作Mapper
* @createDate 2024-09-16 00:01:46
* @Entity generator.domain.Image
*/
public interface ImageMapper extends BaseMapper<Image> {
    /**
     * 根据图片分类id统计图片数量
     * @param imageClassIds
     * @return
     */
    @Select("<script>" +
            "SELECT image_class_id as imageClassId, COUNT(*) as count " +
            "FROM image " +
            "WHERE image_class_id IN " +
            "<foreach collection='imageClassIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "GROUP BY image_class_id" +
            "</script>")
    List<ImageCountDTO> countImagesByClassIds(@Param("imageClassIds") List<Long> imageClassIds);

    /**
     * 根据分类ID查询图片列表
     * @param page 分页参数
     * @param imageClassId 分类ID
     * @return 分页查询结果
     */
    @Select("SELECT * FROM image WHERE image_class_id = #{imageClassId}")
    IPage<Image> selectImagesByClassId(Page<Image> page, @Param("imageClassId") Long imageClassId);
}



