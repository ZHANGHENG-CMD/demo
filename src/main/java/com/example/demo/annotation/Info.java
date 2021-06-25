package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author 74530
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Info {

    String value() default "tracy";
    boolean isDelete();
}
