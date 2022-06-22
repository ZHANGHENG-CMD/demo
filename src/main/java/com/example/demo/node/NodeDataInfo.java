package com.example.demo.node;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 存储且定时刷新节点信息
 */
@Component
public class NodeDataInfo implements Serializable {
    public Long timestamp;
    private transient final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
    private volatile HostInfo hostInfo;
    private String nodeId;
    private AtomicInteger nodeSeq = new AtomicInteger(0);
    private AtomicInteger nodeTotal = new AtomicInteger(0);

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(NodeDataInfo.class);

    public NodeDataInfo() {
        timestamp = System.currentTimeMillis();
    }

    @PostConstruct
    private void init() {
        hostInfo = new HostInfo();
        this.nodeId = hostInfo.getAddress();
        executor.scheduleAtFixedRate(this::refreshNodeInfo, 1, 1, TimeUnit.SECONDS);
        //涉及到io操作和refreshNodeInfo()方法分开避免频繁更新以及IO时间不稳定导致互相影响
        executor.scheduleAtFixedRate(() -> {
            try {
                Map<String, String> nodeMap = redisTemplate.opsForHash().entries("node-info");
                //取所有map信息，遍历map，时间大于activeTime 10s，删掉
                if(!nodeMap.isEmpty()){
                    List<String> deleteKeys = new ArrayList<>();
                    Iterator<Map.Entry<String, String>> iter = nodeMap.entrySet().iterator();
                    while(iter.hasNext()){
                        Map.Entry<String, String> entry=iter.next();
                        long timeDiff = System.currentTimeMillis() - Long.parseLong(entry.getValue());
                        if(timeDiff>=10000){
                            deleteKeys.add(entry.getKey());
                            iter.remove();
                        }
                    }
                    if(CollectionUtils.isNotEmpty(deleteKeys)){
                        Object[] args = deleteKeys.toArray(new String[0]);
                        redisTemplate.opsForHash().delete("node-info", args);
                    }
                }
                //修改当前节点的最新时间timestamp
                timestamp = System.currentTimeMillis();
                redisTemplate.opsForHash().put("node-info",getNodeId(),timestamp.toString());
                nodeMap.put(getNodeId(),timestamp.toString());
                this.setNodeTotal(nodeMap.size());
                List<String> nodeMapKey = nodeMap.keySet().stream().sorted().collect(Collectors.toList());
                int seq = 0;
                for (String key : nodeMapKey) {
                    if (nodeId.equals(key)) {
                        break;
                    }
                    seq++;
                }
                this.setNodeSeq(seq);
            } catch (Exception e) {
                logger.info("发送节点ID失败",e);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }


    /**
     * 刷新节点信息
     */
    public void refreshNodeInfo() {
        hostInfo = new HostInfo();
    }

    public int getNodeSeq() {
        return this.nodeSeq.get();
    }

    public int getNodeTotal() {
        return this.nodeTotal.get();
    }

    public NodeDataInfo setNodeSeq(int nodeSeq) {
        this.nodeSeq.set(nodeSeq);
        return this;
    }

    public NodeDataInfo setNodeTotal(int nodeTotal) {
        this.nodeTotal.set(nodeTotal);
        return this;
    }

    public String getNodeId() {
        return nodeId;
    }
}

