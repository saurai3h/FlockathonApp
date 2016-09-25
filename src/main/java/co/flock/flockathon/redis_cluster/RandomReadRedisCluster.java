/*
 * Copyright (C) 2016 Media.net Advertising FZ-LLC All Rights Reserved
 */

package co.flock.flockathon.redis_cluster;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by prabhat on 11/2/16.
 */

public class RandomReadRedisCluster implements Serializable{

    private List<JedisPool> jedisPools;
    private List<ServerInstance> instances;
    private static RandomReadRedisCluster instance;

    private RandomReadRedisCluster(JedisPoolConfig poolConfig, String redisServerList) throws Exception {
        this.jedisPools=new ArrayList<>();
        this.instances = new ArrayList<>();
        if(redisServerList==null || redisServerList.equals("")) return;

        List<ServerInstance> instances = ServerInstance.parseList(redisServerList);

        for(ServerInstance instance: instances){
            this.instances.add(instance);
            this.jedisPools.add(new JedisPool(poolConfig,instance.getHost(),instance.getPort()));
        }
    }

    private RandomReadRedisCluster(String redisServerList) throws Exception {
        this(defaultPoolConfig(), redisServerList);
    }

    private static JedisPoolConfig defaultPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMinIdle(10);
        config.setMaxWaitMillis(10L);
        config.setTimeBetweenEvictionRunsMillis(30000L);

        config.setTestOnBorrow(true);
        config.setTestOnReturn(false);

        config.setNumTestsPerEvictionRun(10);
        config.setBlockWhenExhausted(GenericObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED);

        return config;
    }

    public JedisPool getRandomJedisPool(){
        return jedisPools.get(new Random().nextInt(jedisPools.size()));
    }

    private boolean isEmpty(){
        return jedisPools.size()==0;
    }

    private List<ServerInstance> getInstances() {
        return instances;
    }

    public static RandomReadRedisCluster getInstance(String redisServerList) throws Exception {
        if(instance == null)    {
            synchronized (RandomReadRedisCluster.class) {
                if(instance == null)    {
                    instance = new RandomReadRedisCluster(redisServerList);
                }
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        return StringUtils.join(instances.toArray(),",");
    }
}
