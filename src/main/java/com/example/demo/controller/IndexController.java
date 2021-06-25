package com.example.demo.controller;

import com.example.demo.annotation.ConversionPoint;
import com.example.demo.annotation.MyLog;
import com.example.demo.annotation.StringConversion;
import com.example.demo.app.SourceAccessService;
import com.example.demo.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 14:38
 */
@RestController
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SourceAccessService service;

    @GetMapping("/sourceA")
    public String sourceA(){
        return "你正在访问sourceA资源";
    }

    @GetMapping("/sourceB")
    public String sourceB(String str){
        return "你正在访问sourceB资源结果："+service.selectByParam(str);
    }

    @MyLog
    @GetMapping("/sourceC/{data}")
    public String sourceC(@PathVariable("data") String data){
        return "你正在访问sourceC资源";
    }

    @GetMapping("/sourceE")
    @ConversionPoint
    public ResponseEntity<Person> sourceD(Person person){
        log.info("person {}",person.getNameList());
        return ResponseEntity.ok(person);
    }
}
