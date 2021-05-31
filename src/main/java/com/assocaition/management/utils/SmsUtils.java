package com.assocaition.management.utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Duanjianhui
 * @date 2021-04-28 12:56 下午
 * @describe
 */
public class SmsUtils {
    public static String getSmsCode() {
        StringBuffer flag = new StringBuffer();
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return flag.toString();
    }

    public static Map<String,String> sendSms(String mobile, String code) {
        Map<String, String> map = new HashMap<>();
        String host = "https://msg.market.alicloudapi.com";
        String path = "/msg";
        String method = "POST";
        String appcode = "bb50830da8694077900a4d08bd816bf7";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("message", "您的验证码是"+code+"。如非本人操作，请忽略本短信");
        bodys.put("mobile", mobile);
        bodys.put("mouldno", "44CAD037");
        bodys.put("title", "云码通");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String s = EntityUtils.toString(response.getEntity());
            map = JSONArray.parseObject(s, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errcode","22222");
            map.put("errmsg","程序异常");
            return map;
        }
        return map;
    }
    public static void main(String[] args) {
        getSmsCode();
    }
}
