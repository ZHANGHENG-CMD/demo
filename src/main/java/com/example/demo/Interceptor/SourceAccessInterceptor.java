package com.example.demo.Interceptor;


import com.example.demo.annotation.LoginRequired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 14:42
 */
public class SourceAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器了");

        // 反射获取方法上的LoginRequred注解
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        LoginRequired annotation = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        if(annotation == null){
            return true;
        }
        // 有LoginRequired注解说明需要登录，提示用户登录
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println("访问需要登录");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
