package com.example.demo.test;

import com.example.demo.annotation.MyField;
import lombok.Data;

import java.lang.reflect.Field;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 15:21
 */
@Data
public class MyFieldTest {

    //使用我们的自定义注解
    @MyField(description = "用户名", length = 12)
    private String username;


    public static void main(String[] args) {
        // 获取类模板
        Class c = MyFieldTest.class;
        // 获取所有字段
        for(Field f:c.getDeclaredFields()){
            // 判断这个字段是否有MyField注解
            if(f.isAnnotationPresent(MyField.class)){
                MyField annotation = f.getAnnotation(MyField.class);
                System.out.println("字段："+f.getName()+" 描述："+annotation.description()+" 长度："+annotation.length());
            }
        }
    }

}
