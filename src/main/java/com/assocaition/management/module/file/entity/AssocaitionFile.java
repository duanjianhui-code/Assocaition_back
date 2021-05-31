package com.assocaition.management.module.file.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Duanjianhui
 * @date 2021-04-16 3:12 下午
 * @describe
 */
@Data
public class AssocaitionFile implements Serializable {
    //文件ID（uuid）
    private String id;
    //文件名
    private String fileName;
    //oss-文件地址
    private String ossUrl;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date uploadTime;
    //上传者
    private String uploadUser;
    //是否有效
    private String isDelete;
    //文件所属社团ID
    private String assId;
    //文件类型,1为头像，2为
    private String type;

}