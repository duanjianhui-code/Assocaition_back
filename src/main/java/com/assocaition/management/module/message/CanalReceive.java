package com.assocaition.management.module.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-30 2:24 上午
 * @describe
 */
@Component
public class CanalReceive {
    @Autowired
    private RedisTemplate redisTemplate;

//    @RabbitListener(queues = "exchange.trade.canal")
    public void process(Message message) throws UnsupportedEncodingException{
        //获取到消息队列的消息
        String json = new String(message.getBody());
        Map map = JSON.parseObject(json, Map.class);

        JSONArray jsonArray = null;
        String sqlType = (String) map.get("type");
        if (StringUtils.endsWithIgnoreCase("select",sqlType)){
            jsonArray = JSONArray.parseArray((String) map.get("data"));
        } else {
            jsonArray = (JSONArray) map.get("data");
        }
        JSONObject object = jsonArray.getJSONObject(0);
        if (StringUtils.endsWithIgnoreCase("update",sqlType)
        || StringUtils.endsWithIgnoreCase("insert",sqlType)
                || StringUtils.endsWithIgnoreCase("select",sqlType)
        ){
            redisTemplate.boundValueOps("id").set(object.toString());
        } else if (StringUtils.endsWithIgnoreCase("delete",sqlType) ){
            redisTemplate.delete(object.get("id"));
        }
    }
}
