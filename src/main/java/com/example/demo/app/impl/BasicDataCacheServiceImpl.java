package com.example.demo.app.impl;

import com.example.demo.app.BasicDataCacheService;
import com.example.demo.common.entity.BasicDataGroupDTO;
import com.example.demo.exception.CommonException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/4/16 17:02
 */
@Service
@Slf4j
public class BasicDataCacheServiceImpl implements BasicDataCacheService {

    /**
     * 基础信息本地缓存
     */
    protected static final LoadingCache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(10)
            .expireAfterWrite(10, TimeUnit.MINUTES).build(queryCache());

    protected static CacheLoader<String, Object> queryCache() {
        return new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) throws Exception {
                return "返回:"+key;
            }
        };
    }

    public static void main(String[] args) throws ExecutionException {
        String jery = (String)localCache.get("jery");
        System.out.println(jery);
        String tom = (String)localCache.get("tom");
        System.out.println(tom);
    }
}
