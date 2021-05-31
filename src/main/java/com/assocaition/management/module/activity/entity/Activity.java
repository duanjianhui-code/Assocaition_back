package com.assocaition.management.module.activity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-23 8:29 下午
 * @describe
 */
@Data
public class Activity implements Serializable {
    //id
    private String id;
    //活动主题
    private String theme;
    //活动内容
    private String content;
    //活动部门ID
    private String assId;
    //申请人
    private String applyUser;
    //申请时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date applyDate;
    //处理人
    private String  disposeUser;
    //处理时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date disposeDate;
    //活动申请状态:0--申请中，1--申请通过，2--申请不通过
    private String status;
    //审批意见
    private String opinion;
    //活动图片
    private String activityImg;
    //用户id
    private Integer userId;
    //文件地址
    private String fileUrl;
}
