package com.assocaition.management.module.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-04-25 2:18 下午
 * @describe
 */
@Data
public class User implements Serializable {
    //id
    private String id;
    //用户名
    private String UserName;
    //密码
    private String password;
    //性别
    private String sex;
    //年龄
    private String age;
    //电话
    private String phone;
    //邮件
    private String email;
    //


}
