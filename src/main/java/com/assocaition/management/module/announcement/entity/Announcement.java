package com.assocaition.management.module.announcement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-23 9:27 下午
 * @describe
 */
@Data
public class Announcement implements Serializable {
    //ID
    private String id;
    //通知主题
    private String theme;
    //通知内容
    private String content;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    //创建人
    private String createUser;
    //是否有效
    private String isEnable;
    //社团ID
    private String assId;

}
