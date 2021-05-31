package com.assocaition.management.module.assocaition.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-04-22 12:15 下午
 * @describe
 */
@Data
public class Assocaition implements Serializable {
    //ID
    private String id;
    //社团名称
    private String assName;
    //社团简介
    private String introduction;
    //创建人
    private String createUser;
    //创建人ID
    private Integer userId;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    //社团状态
    private String status;
    //社团类型
    private String type;
    //父ID
    private String parentId;
    //社团邮件
    private String associationEmail;
    //社团电话
    private String telephone;
    //社团列表图片
    private String indexImageUrl;
    //审核意见
    private String opinion;
    //轮播图
    private List fileList;
    //报名费
    private String entryFee;
}
