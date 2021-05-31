package com.assocaition.management.utils;

import java.util.UUID;

/**
 * @author Duanjianhui
 * @date 2021-04-17 10:16 上午
 * @describe
 */
public class UUIDUtils {
    /**
     * 获取UUID
     * @param type
     * @return
     */
    public static String getUUID(String type){
        String id = type+"-"+UUID.randomUUID().toString().replace("/", "-");
        return id;
    }
}
