package com.hzw.delay_queue.redis_version.model;

/*
*@author: huangzhiwen
*@date: 2019/9/4 20:41
*/
public class Message {

    private int id;
    //单位（秒）
    private int ttl;
    private String queueName;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
