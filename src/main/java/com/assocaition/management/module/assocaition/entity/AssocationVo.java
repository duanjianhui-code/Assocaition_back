package com.assocaition.management.module.assocaition.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-05-12 3:14 下午
 * @describe
 */
@Data
public class AssocationVo implements Serializable {
    private int page;
    private int limit;
    private String title;
    private String type;
}
