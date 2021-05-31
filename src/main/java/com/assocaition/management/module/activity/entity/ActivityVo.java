package com.assocaition.management.module.activity.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-05-21 1:19 下午
 * @describe
 */
@Data
public class ActivityVo implements Serializable {
    private int page;
    private int limit;
    private String theme;
    private String applyUser;
    private String assId;
}
