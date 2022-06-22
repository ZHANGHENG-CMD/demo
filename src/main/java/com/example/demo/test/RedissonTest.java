package com.example.demo.test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 74530
 */
public class RedissonTest {

    private static final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

    public static void main(String[] args) throws InterruptedException {
        doing("test1");
    }

    public static void doing(String name) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getLock("redis-lock");
        AtomicBoolean b = new AtomicBoolean(lock.tryLock(10, TimeUnit.SECONDS));
        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            if(b.get()){
                System.out.println(name+"==============success===========");
            }else {
                try {
                    b.set(lock.tryLock(10, TimeUnit.SECONDS));
                } catch (Exception e) {
                }
                System.out.println(name+"==============failed===========");
            }
        },100,5000,TimeUnit.MILLISECONDS);
    }
}
