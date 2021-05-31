package com.assocaition.management.module.examine.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-05-21 11:35 上午
 * @describe
 */
@Data
public class ExamineVo implements Serializable {
    private int page;
    private int limit;
    private String assId;
    private String submitUser;
}
