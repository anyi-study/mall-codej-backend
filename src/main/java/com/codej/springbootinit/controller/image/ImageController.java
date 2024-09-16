package com.codej.springbootinit.controller.image;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.PageResponse;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.dto.admin_image.ImageClassRequest;
import com.codej.springbootinit.model.entity.image.Image;
import com.codej.springbootinit.model.entity.image.ImageClass;
import com.codej.springbootinit.model.vo.ImageResponse;
import com.codej.springbootinit.service.ImageClassService;
import com.codej.springbootinit.service.ImageService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "图库管理")
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageClassService imageClassService;


    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public BaseResponse<List<ImageResponse>> uploadImage(@RequestHeader(value = "token") String token,
                                                         @RequestParam("image_class_id") Integer imageClassId,
                                                         @RequestParam("img[]") MultipartFile[] files) {
        // 验证 token
        String username = JwtUtil.extractUsername(token);
        if (username == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 调用服务层上传图片
        List<ImageResponse> uploadedImages = imageService.uploadImages(imageClassId, files);

        // 返回上传结果
        return ResultUtils.success(uploadedImages);
    }

    /**
     * 删除图片
     * @param token
     * @param requestBody
     * @return
     */
    @ApiOperation("删除图片")
    @PostMapping("/delete_all")
    public ResponseEntity<?> deleteImages(@RequestHeader("token") String token, @RequestBody Map<String, List<Integer>> requestBody) {
        List<Integer> ids = requestBody.get("ids");
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "No image IDs provided");
        }

        boolean result = imageService.deleteImages(ids);
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "ok");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }
    @ApiOperation("修改图片名称")
    @PostMapping("/admin/image/{id}")
    public ResponseEntity<Map<String, Object>> updateImageName(
            @RequestHeader("token") String token,
            @PathVariable("id") Integer imageId,
            @RequestParam("name") String name) {

        // 假设 imageService.updateImageName() 更新图片名称并返回是否成功
        boolean result = imageService.updateImageName(imageId, name);

        // 创建响应的 Map
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "ok");
        response.put("data", result);

        // 返回成功的响应
        return ResponseEntity.ok(response);
    }

}
