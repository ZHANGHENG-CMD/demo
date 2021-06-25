package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 74530
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringConversion {

    String trim() default ",";

    String assignmentField() default "";

    /**
     * 根据指定值添加，支持string类型
     */
    String [] addValueByTarget() default {};

    String targetValue() default "";
}
