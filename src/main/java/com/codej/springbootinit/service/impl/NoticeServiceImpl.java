package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.mapper.NoticeMapper;
import com.codej.springbootinit.model.entity.notice.Notice;
import com.codej.springbootinit.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10306
 * @description 针对表【notice(公告表)】的数据库操作Service实现
 * @createDate 2024-09-17 15:37:59
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Notice addNotice(String title, String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateTime(System.currentTimeMillis());
        notice.setUpdateTime(System.currentTimeMillis());

        int rows = noticeMapper.insert(notice);
        if (rows > 0) {
            return notice;  // 成功插入，返回新公告对象
        }
        return null;  // 插入失败
    }

    @Override
    public boolean updateNotice(Long id, String title, String content) {
        Notice notice = noticeMapper.selectById(id);
        if (notice != null) {
            notice.setTitle(title);
            notice.setContent(content);
            notice.setUpdateTime(System.currentTimeMillis());
            return noticeMapper.updateById(notice) > 0;
        }
        return false;
    }

    @Override
    public List<Notice> listNotices(int page) {

        // 分页查询，假设每页10条数据
        Page<Notice> noticePage = new Page<>(page, 10);
        IPage<Notice> noticeIPage = noticeMapper.selectPage(noticePage, null);
        // 获取分页结果中的公告列表并返回
        List<Notice> noticeList = noticeIPage.getRecords();

        return noticeList;
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @Override
    public Boolean deleteNotice(int id) {
//        参数校验
        if (id <= 0) {
            throw new RuntimeException("参数不合法");
        }
        return noticeMapper.deleteById(id) > 0;
    }
}




