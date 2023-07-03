package com.example.demo.annotation;

import com.example.demo.enums.BusinessType;
import com.example.demo.enums.OperatorType;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";
    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;
    /**
     * 操作人类型
     */
    OperatorType operatorType() default OperatorType.MANAGE;
    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;
    /**
     * 记录返回文件
     */
    boolean recordReturnFile() default false;
    /**
     * 记录请求文件
     */
    boolean recordRequestFile() default false;
}
