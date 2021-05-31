package com.assocaition.management.module.announcement.service.serviceimpl;

import com.assocaition.management.module.announcement.dao.AnnouncementMapper;
import com.assocaition.management.module.announcement.entity.Announcement;
import com.assocaition.management.module.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Duanjianhui
 * @date 2021-05-13 3:00 下午
 * @describe
 */
@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    AnnouncementMapper announcementMapper;
    @Override
    public int addAssocaitionAnnouncement(Announcement announcement) {
        return announcementMapper.addAssocaitionAnnouncement(announcement);
    }

    @Override
    public Announcement selectAnnouncementById(String id) {
        return announcementMapper.selectAnnouncementById(id);
    }
}
