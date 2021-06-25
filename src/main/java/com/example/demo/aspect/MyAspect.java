package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 15:52
 */
@Component
@Aspect
public class MyAspect {

    /**
     * PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
     * 切面最主要的就是切点，所有的故事都围绕切点发生
     * logPointCut()代表切点名称
     */
    @Pointcut("@annotation(com.example.demo.annotation.MyLog)")
    public void logPointCut(){}

    /**
     * 环绕通知,因为被MyLogAspect类切面拦截住了，字符串结果是joinPoint.proceed();的执行结果，没有用变量接住。如果改为String result = joinPoint.proceed();接住返回就可以了
     */
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        //获取方法名称
        String methodName  = joinPoint.getSignature().getName();
        //获取入参
        Object[] args = joinPoint.getArgs();

        StringBuilder stringBuilder = new StringBuilder();
        for(Object o:args){
            stringBuilder.append(o).append(";");
        }

        System.out.println("进入[" + methodName + "]方法,参数为:" + stringBuilder.toString());

        Object p = null;
        try{
            p= joinPoint.proceed();
        }catch (Throwable  e){
            e.printStackTrace();
        }
        System.out.println(methodName + "方法执行结束");
        return p;
    }
}
