package com.codej.springbootinit.controller.admin_image;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.admin_image.ImageClassRequest;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.ManagerPageResponse;
import com.codej.springbootinit.model.entity.image.Image;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.service.ImageClassService;
import com.codej.springbootinit.service.ImageService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "图片接口")
@RestController
@RequestMapping("/image_class")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageClassService imageClassService;


    /**
     * 删除图库分类
     * @param token
     * @param id
     * @return
     */
    @ApiOperation("删除图库分类")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteImageClass(@RequestHeader(value = "token") String token,
                                                  @PathVariable("id") Long id) {
        // 验证 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 调用服务层删除分类
        boolean success = imageClassService.deleteImageClass(id);

        // 返回成功响应
        return ResultUtils.success(success);
    }
    /**
     * 修改图库分类
     * @param token
     * @param id
     * @param name
     * @param order
     * @return
     */
    @ApiOperation("修改图库分类")
    @PostMapping("/{id}")
    public BaseResponse<Boolean> updateImageClass(@RequestHeader(value = "token") String token,
                                                  @PathVariable("id") Long id,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("order") Integer order) {
        // 验证 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 调用服务层更新分类
        boolean success = imageClassService.updateImageClass(id, name, order);

        // 返回成功响应
        return ResultUtils.success(success);
    }
    /**
     * 增加图库分类
     * @param token
     * @param name
     * @param order
     * @return
     */

    @ApiOperation("增加图库分类")
    @PostMapping
    public BaseResponse<ImageClass> addImageClass(@RequestHeader(value = "token") String token,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("order") Integer order) {
        // 验证 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 调用服务层保存分类
        ImageClass newImageClass = imageClassService.addImageClass(name, order);

        // 返回成功响应
        return ResultUtils.success(newImageClass);
    }

    /**
     * 获取指定分类下的图片列表
     * @param token
     * @param imageClassId
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("获取指定分类下的图片列表")
    @GetMapping("/{id}/image/{page}")
    public BaseResponse<PageResponse<Image>> getImagesByClassId(@RequestHeader(value = "token") String token,
                                                                @PathVariable("id") Long imageClassId,
                                                                @PathVariable("page") Integer page,
                                                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        // 验证 token (假设你有 JwtUtil 类来验证 token)
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 调用服务层查询
        PageResponse<Image> imageList = imageService.getImagesByClassId(imageClassId, page, limit);

        // 返回成功响应
        return ResultUtils.success(imageList);
    }
    /**
     * 获取图库列表
     *
     * @param token
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("获取图库列表")
    @GetMapping("/{page}")
    public BaseResponse<PageResponse<ImageClassRequest>> getImage(@RequestHeader(value = "token") String token,
                                                           @PathVariable("page") Integer page,
                                                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        // 获取请求头中的 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 请求参数校验
        if (page <= 0 || limit <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 调用服务层方法，获取分页数据
        PageResponse<ImageClassRequest> response = imageService.getImageList(page, limit);

        // 返回成功响应
        return ResultUtils.success(response);
    }

}
