package com.assocaition.management.module.task.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-22 11:13 下午
 * @describe
 */
@Data
public class Task implements Serializable {
    // ID(主键)
    private String  id;
    // 业务记录ID
    private String  businessId;
    // 业务模块名
    private String  businessName;
    // 业务跳转地址
    private String  businessUrl;
    // 指派人ID
    private Integer  assignTo;
    // 完成人ID
    private Integer  completeBy;
    // 完成日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")    private Date  completeDate;
    // 创建人ID
    private Integer  createManId;
    // 创建人
    private String  createName;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")    private Date  createTime;
    //状态 state-0未处理 state-1处理中 state-2处理完
    private String  state;

}
