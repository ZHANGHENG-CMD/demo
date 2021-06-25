package com.example.demo;

import com.example.demo.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;


/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/4/21 16:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one(){
        final String content="RedisTemplate 实战字符串";
        final String key="redis:template:one:string";

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //valueOperations.set(key,content);
        Object o = valueOperations.get(key);
        log.info("读取内容：{}",o);
    }

    @Test
    public void two() throws JsonProcessingException {
        Person person = new Person();
        person.setAge(18);
        person.setName("tom");
        person.setDelete(true);

        final String key = "redis:test:1";
        String value = objectMapper.writeValueAsString(person);
        redisTemplate.opsForValue().set(key,value);
        Object o = redisTemplate.opsForValue().get(key);
        if(Objects.nonNull(o)){
            Person person1 = objectMapper.readValue(o.toString(), Person.class);
            log.info("缓存读取：{}",person1);
        }
    }
}
