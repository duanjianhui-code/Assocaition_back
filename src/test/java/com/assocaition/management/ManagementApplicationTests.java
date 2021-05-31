package com.assocaition.management;

import com.alibaba.fastjson.JSONArray;
import com.assocaition.management.utils.EmailUtils;
import com.assocaition.management.utils.HttpUtils;
import com.assocaition.management.utils.RedisUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ManagementApplicationTests {
    @Autowired EmailUtils emailUtils;
    @Autowired
    RedisUtil redisUtil;
    @Test
    void contextLoads() {
        File file = new File("/Users/duanjianhui/Downloads/下载.mp4");
        emailUtils.doSendHtmlEmail("cesh","csds",new String[]{"2623424675@qq.com"},file);
    }
    @Test
    void send () {
        String key = "sms:18173928033";
        redisUtil.set(key,"123456",60000);
        //redisTemplate.opsForValue().set(key, "123456");
    }
}
