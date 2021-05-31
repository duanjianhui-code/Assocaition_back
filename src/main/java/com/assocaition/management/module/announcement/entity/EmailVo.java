package com.assocaition.management.module.announcement.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author Duanjianhui
 * @date 2021-05-13 8:34 下午
 * @describe
 */
@Data
public class EmailVo implements Serializable {
    private String email;
    //主题
    private String subject;
    //内容
    private String sendHtml;
}
