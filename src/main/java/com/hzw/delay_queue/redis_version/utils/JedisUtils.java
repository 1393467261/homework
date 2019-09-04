package com.hzw.delay_queue.redis_version.utils;
import	java.util.Set;

import com.hzw.delay_queue.redis_version.model.QueuesConf;
import redis.clients.jedis.Jedis;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/1 17:14
 * Description:
 */
public class JedisUtils{

    private static QueuesConf conf;

    static {
        conf = ConfUtils.getConf();
    }

    public static JedisTemplate getJedisTemplate(QueuesConf queuesConf) {
        return new JedisTemplate(queuesConf);
    }

    public static void close(Jedis jedis) {
        jedis.close();
    }

    public static final class JedisTemplate{

        private QueuesConf queuesConf;
        private Jedis jedis;

        private JedisTemplate(QueuesConf queuesConf) {
            this.queuesConf = queuesConf;
            this.jedis = new Jedis(queuesConf.getRedisHost(), queuesConf.getRedisPort());
        }

    }
}
