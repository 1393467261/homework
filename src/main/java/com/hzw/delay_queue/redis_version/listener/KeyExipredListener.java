package com.hzw.delay_queue.redis_version.listener;

import com.hzw.delay_queue.redis_version.handler.KeyExpiredHandler;
import redis.clients.jedis.JedisPubSub;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/4 23:22
 * Description:
 */
public class KeyExipredListener extends JedisPubSub {

    private KeyExpiredHandler handler;

    public KeyExipredListener(KeyExpiredHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe "
                + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

        System.out.println("onPMessage pattern "
                + pattern + " " + channel + " " + message);
    }
}
