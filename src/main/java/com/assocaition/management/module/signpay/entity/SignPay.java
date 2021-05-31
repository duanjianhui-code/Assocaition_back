package com.assocaition.management.module.signpay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-25 1:32 下午
 * @describe
 */
@Data
public class SignPay implements Serializable {
    //id
    private String id;
    //社团ID
    private String assId;
    //报名费用
    private int registrationFee;
    //创建人
    private String createUser;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;


}
