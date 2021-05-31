package com.assocaition.management.module.announcement.service;

import com.assocaition.management.module.announcement.entity.Announcement;

/**
 * @author Duanjianhui
 * @date 2021-05-13 2:59 下午
 * @describe
 */
public interface AnnouncementService {
    int addAssocaitionAnnouncement(Announcement announcement);

    Announcement selectAnnouncementById(String id);
}
