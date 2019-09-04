package com.hzw.delay_queue.redis_version;

import com.google.gson.Gson;
import com.hzw.delay_queue.redis_version.listener.KeyExipredListener;
import com.hzw.delay_queue.redis_version.model.Message;
import com.hzw.delay_queue.redis_version.model.QueuesConf;
import com.hzw.delay_queue.redis_version.utils.ConfUtils;
import com.hzw.delay_queue.redis_version.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Set;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/1 17:04
 * Description:
 */
public class Test {



//        QueuesConf conf = ConfUtils.getConf();
//        QueuesConf conf2 = ConfUtils.getConf("conf2.json");
//
//        System.out.println(conf);
//        System.out.println(conf2);
//
//        JedisUtils.JedisTemplate jedisTemplate = JedisUtils.getJedisTemplate(conf);
//        Set<String> values = jedisTemplate.zrange(0, 144);
//        System.out.println(values);
//        jedisTemplate.close();

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.psubscribe(new KeyExipredListener(), "__keyevent@0__:expired");
    }
}
