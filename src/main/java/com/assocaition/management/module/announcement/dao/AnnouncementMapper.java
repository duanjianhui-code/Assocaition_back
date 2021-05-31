package com.assocaition.management.module.announcement.dao;

import com.assocaition.management.module.announcement.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Duanjianhui
 * @date 2021-05-13 2:59 下午
 * @describe
 */
@Mapper
public interface AnnouncementMapper {
    int addAssocaitionAnnouncement(Announcement announcement);

    Announcement selectAnnouncementById(String id);
}
