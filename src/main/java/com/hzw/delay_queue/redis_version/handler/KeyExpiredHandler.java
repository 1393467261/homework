package com.hzw.delay_queue.redis_version.handler;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/5 0:07
 * Description:
 */
public class KeyExpiredHandler extends Handler {

    @Override
    protected void handler(String s) {
        //todo 发送到延迟队列
        super.handler(s);
    }
}
