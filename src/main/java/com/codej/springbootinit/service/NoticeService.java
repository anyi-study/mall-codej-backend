package com.codej.springbootinit.service;

import com.codej.springbootinit.model.entity.notice.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 10306
* @description 针对表【notice(公告表)】的数据库操作Service
* @createDate 2024-09-17 15:37:59
*/
public interface NoticeService extends IService<Notice> {

    /**
     * 添加公告
     * @param title
     * @param content
     * @return
     */
    Notice addNotice(String title, String content);

    /**
     * 更新公告
     * @param id
     * @param title
     * @param content
     * @return
     */
    boolean updateNotice(Long id, String title, String content);

    /**
     * 获取公告列表
     * @param page
     * @return
     */
    List<Notice> listNotices(int page);

    /**
     * 删除公告
     * @param id
     * @return
     */
    Boolean deleteNotice(int id);
}
