package com.example.demo.pull;

import com.example.demo.node.NodeDataInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author heng.zhang06@going-link.com
 */
@Component
public class PullTaskExecutor {

    public static final ConcurrentHashMap<String, String> MAP = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NodeDataInfo nodeDataInfo;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    @Lazy
    private PullTaskExecutor pullTaskExecutor;

    private static final Logger logger = LoggerFactory.getLogger(PullTaskExecutor.class);

    private static final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            30,
            50,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(30),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 执行启动
     */
    public void start() {
        // 定时查询未执行的任务并执行
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                POOL_EXECUTOR.getActiveCount();
                nodeDataInfo.getNodeTotal();
                nodeDataInfo.getNodeSeq();
                MAP.put("1", "1");
                POOL_EXECUTOR.execute(() -> pullTaskExecutor.executeTask());
            } catch (Exception e) {
                logger.error("pull task error at Executors:", e);
            }
        }, 0, 3, TimeUnit.SECONDS);
        pushMap();
    }

    /**
     * 执行中的数据隔10s放入redis中,30s过期
     */
    private void pushMap() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                if (MAP.size() > 0) {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    redisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
                        MAP.forEach((key, value) -> {
                            redisConnection.set(Objects.requireNonNull(serializer.serialize(key)), Objects.requireNonNull(serializer.serialize(value)));
                            redisConnection.expire(Objects.requireNonNull(serializer.serialize(key)), 30);
                        });
                        return null;
                    });

                    List<Object> list = redisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
                        MAP.forEach((key, value) -> {
                            redisConnection.get(Objects.requireNonNull(serializer.serialize(key)));
                            redisConnection.expire(Objects.requireNonNull(serializer.serialize(key)), 30);
                        });
                        return null;
                    });
                }
            } catch (Exception e) {
                logger.error("put data to redis error :", e);
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    @Transient
    public void executeTask() {
        try {

        } catch (Exception e) {

        } finally {
            MAP.remove("1");
            redisTemplate.delete("key");
        }
    }
}

