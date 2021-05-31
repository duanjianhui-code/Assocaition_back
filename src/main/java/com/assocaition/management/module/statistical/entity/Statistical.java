package com.assocaition.management.module.statistical.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-04-25 1:32 下午
 * @describe
 */
@Data
public class Statistical implements Serializable {
    //id
    private String id;
    //社团ID
    private String assId;
    //社团（子部门）数量
    private int associationCount;
    //社团成员数量
    private int memberCount;
    //文件量
    private int fileCount;
    //文件下载量
    private int downloadsCount;
    //统计时间
    private int statisticalTime;
}
