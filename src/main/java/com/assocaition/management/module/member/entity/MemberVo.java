package com.assocaition.management.module.member.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-05-20 9:40 下午
 * @describe
 */
@Data
public class MemberVo implements Serializable {
    private int page;
    private int limit;
    private String assId;
    private String memberName;
    private String memberSex;
    private String memberAge;
}
