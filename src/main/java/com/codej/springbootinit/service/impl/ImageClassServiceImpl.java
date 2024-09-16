package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.mapper.ImageClassMapper;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.service.ImageClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 10306
* @description 针对表【image_class(相册表)】的数据库操作Service实现
* @createDate 2024-09-16 00:01:49
*/
@Service
public class ImageClassServiceImpl extends ServiceImpl<ImageClassMapper, ImageClass>
    implements ImageClassService {
    @Autowired
    private ImageClassMapper imageClassMapper;

    @Override
    public ImageClass addImageClass(String name, Integer order) {
        // 构造 ImageClass 对象
        ImageClass imageClass = new ImageClass();
        imageClass.setName(name);
        imageClass.setOrders(order); // 假设表中的字段是 orders

        // 插入到数据库
        imageClassMapper.insert(imageClass);

        // 返回新增的对象，包含 ID
        return imageClass;
    }

    /**
     * 修改图库分类
     * @param id
     * @param name
     * @param order
     * @return
     */
    @Override
    public boolean updateImageClass(Long id, String name, Integer order) {
        // 查询是否存在此分类
        ImageClass imageClass = imageClassMapper.selectById(id);
        if (imageClass == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }

        // 更新分类信息
        imageClass.setName(name);
        imageClass.setOrders(order); // 假设表中的字段是 orders

        // 执行更新操作
        int rowsAffected = imageClassMapper.updateById(imageClass);

        // 判断更新是否成功
        return rowsAffected > 0;
    }

    /**
     * 删除图库分类
     * @param id
     * @return
     */
    @Override
    public boolean deleteImageClass(Long id) {
        // 查询是否存在此分类
        ImageClass imageClass = imageClassMapper.selectById(id);
        if (imageClass == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }

        // 执行删除操作
        int rowsAffected = imageClassMapper.deleteById(id);

        // 判断删除是否成功
        return rowsAffected > 0;
    }
}




