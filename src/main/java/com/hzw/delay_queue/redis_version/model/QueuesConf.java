package com.hzw.delay_queue.redis_version.model;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2019/9/1 19:48
 * Description:
 */
public class QueuesConf {

    @SerializedName("redisHost")
    private String redisHost;

    @SerializedName("redisPort")
    private Integer redisPort;

    @SerializedName("queueName")
    private String queueName;

    @SerializedName("delayQueueName")
    private String delayQueueName;

    @SerializedName("timeExpired")
    private Long timeExpired;

    public QueuesConf(String redisHost, Integer redisPort, String queueName, String delayQueueName, Long timeExpired) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.queueName = queueName;
        this.delayQueueName = delayQueueName;
        this.timeExpired = timeExpired;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getDelayQueueName() {
        return delayQueueName;
    }

    public void setDelayQueueName(String delayQueueName) {
        this.delayQueueName = delayQueueName;
    }

    public Long getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(Long timeExpired) {
        this.timeExpired = timeExpired;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }

    @Override
    public String toString() {
        return "QueuesConf{" +
                "redisHost='" + redisHost + '\'' +
                ", redisPort=" + redisPort +
                ", queueName='" + queueName + '\'' +
                ", delayQueueName='" + delayQueueName + '\'' +
                ", timeExpired=" + timeExpired +
                '}';
    }
}
