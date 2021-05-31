package com.assocaition.management.module.examine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-24 11:14 上午
 * @describe
 */
@Data
public class Examine implements Serializable {
    //id
    private String id;
    //部门ID
    private String assId;
    //考核状态:0--审核中，1--审核通过，2--审核不通过
    private String status;
    //考核意见
    private String opinion;
    //考核信息
    private String contentJson;
    //提交人
    private String submitUser;
    //提交时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date submitTime;
    //审核人
    private String auditUser;
    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private String auditTime;
    //分数
    private Integer score;
    //用户id
    private Integer userId;
}
