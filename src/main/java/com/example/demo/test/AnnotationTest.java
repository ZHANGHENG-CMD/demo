package com.example.demo.test;

import com.example.demo.annotation.Info;
import com.example.demo.domain.Person;


/**
 * TODO
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 14:16
 */
public class AnnotationTest {

    public static void main(String[] args) {
        Person person = new Person();
        Class aClass = person.getClass();
        if(aClass.isAnnotationPresent(Info.class)){
          Info info = (Info)aClass.getAnnotation(Info.class);
            System.out.println("person.name :" + info.value() + ",person.isDelete:" + info.isDelete());
        }
    }
}
