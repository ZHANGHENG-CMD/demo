package com.example.demo.Interceptor;

import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 15:42
 */
public class SourceAfterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器B了");
        //请求参数
        String queryString = request.getQueryString();
        System.out.println("请求参数："+queryString);
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("请求参数："+parameterMap.toString());
        String str = request.getParameter("str");
        System.out.println("=======:"+str);
        String[] strs = request.getParameterValues("str");
        System.out.println("=======---:");
        byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
        String s = new String(bytes, request.getCharacterEncoding());
        System.out.println("请求体："+s);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
