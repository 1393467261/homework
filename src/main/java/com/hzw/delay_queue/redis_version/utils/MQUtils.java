package com.hzw.delay_queue.redis_version.utils;

import com.hzw.delay_queue.redis_version.model.Message;
import redis.clients.jedis.Jedis;

import java.util.List;

/*
*@author: huangzhiwen
*@date: 2019/9/4 20:12
*/
public class MQUtils {

    //todo 获取jedis
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);
    private final static int timeout = 1000;


    /*
    * 操作kv
    * */
    public static void sendMessage(Message message) {

    }

    /*
    *操作zmap
    **/
    public static void sendMessageToDelayQueue(Message message) {
        jedis.setex(message.getContent(), message.getTtl(), "");
    }

    /*
    *操作list，左进右出
    **/
    public static List<String> listenOnQueue(String queueName) {
        return jedis.brpop(timeout, queueName);
    }

    public static void sendMessage(String queueName, String[] message) {
        jedis.lpush(queueName, message);
    }
}
