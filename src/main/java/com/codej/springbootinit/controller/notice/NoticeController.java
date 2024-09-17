package com.codej.springbootinit.controller.notice;

import com.codej.springbootinit.common.BaseResponse;
import com.codej.springbootinit.common.ErrorCode;
import com.codej.springbootinit.common.ResultUtils;
import com.codej.springbootinit.exception.BusinessException;
import com.codej.springbootinit.model.entity.notice.Notice;
import com.codej.springbootinit.service.NoticeService;
import com.codej.springbootinit.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("公告接口")
@RequestMapping("/admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @ApiOperation("删除公告")
    @PostMapping("/{id}/delete")
    public BaseResponse<Boolean> deleteNotice(@RequestHeader("token") String token,
                                             @PathVariable("id") int id) {
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
//        参数校验
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = noticeService.deleteNotice(id);
        if (b) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(400, "删除公告不存在");
        }
    }

    /**
     * 获取公告列表
     * @param page
     * @return
     */
    @ApiOperation("获取公告列表")
    @GetMapping("/{page}")
    public BaseResponse<List<Notice>> listNotices(@RequestHeader("token") String token,
                                                  @PathVariable("page") int page) {
//        参数校验
        if (page <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        身份验证
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
        // 调用服务层方法
        List<Notice> result = noticeService.listNotices(page);

        if (result != null) {
            return ResultUtils.success(result);
        } else {
            return ResultUtils.error(400, "获取公告列表失败");
        }
    }
    @ApiOperation("添加公告")
    @PostMapping
    public BaseResponse<Notice> addNotice(@RequestHeader("token") String token,
                                          @RequestParam("title") String title,
                                          @RequestParam("content") String content) {
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
//        参数校验
        if (title == null || content == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Notice notice = noticeService.addNotice(title, content);
        if (notice != null) {
            return ResultUtils.success(notice);
        } else {
            return ResultUtils.error(400, "添加公告失败");
        }
    }

    @ApiOperation("更新公告")
    @PostMapping("/{id}")
    public BaseResponse<Boolean> updateNotice(@RequestHeader("token") String token,
                                              @PathVariable("id") Long id,
                                              @RequestParam("title") String title,
                                              @RequestParam("content") String content) {
        String tokenUsername = JwtUtil.extractUsername(token);
        if (tokenUsername == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        if (JwtUtil.isTokenExpired(token)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "token过期");
        }
//        参数校验
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (title == null || content == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isUpdated = noticeService.updateNotice(id, title, content);
        if (isUpdated) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(400, "更新公告失败");
        }
    }
}
