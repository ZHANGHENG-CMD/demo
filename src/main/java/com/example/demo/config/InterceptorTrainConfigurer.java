package com.example.demo.config;

import com.example.demo.Interceptor.SourceAccessInterceptor;
import com.example.demo.Interceptor.SourceAfterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 15:14
 */
@Configuration
public class InterceptorTrainConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new SourceAccessInterceptor()).addPathPatterns("/sourceD/**");
        //registry.addInterceptor(new SourceAfterInterceptor()).addPathPatterns("/**");
    }
}
