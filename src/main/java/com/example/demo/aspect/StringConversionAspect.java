package com.example.demo.aspect;

import com.example.demo.annotation.StringConversion;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/6/24 14:06
 */
@Component
@Aspect
public class StringConversionAspect {

    private static final Logger log = LoggerFactory.getLogger(StringConversionAspect.class);

    /**
     * PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
     * 切面最主要的就是切点，所有的故事都围绕切点发生
     * conversionPointCut()代表切点名称
     */
    @Pointcut("@annotation(com.example.demo.annotation.ConversionPoint)")
    public void conversionPointCut(){}

    /**
     * 环绕通知,因为被StringConversionAspect类切面拦截住了，字符串结果是joinPoint.proceed();的执行结果，没有用变量接住。如果改为String result = joinPoint.proceed();接住返回就可以了
     */
    @Around("conversionPointCut()")
    public Object conversionAround(ProceedingJoinPoint joinPoint){
        //获取入参
        Object[] args = joinPoint.getArgs();
        for(Object o:args){
            List<Field> fields = getAllFieldsList(o.getClass());
            for(Field field : fields){
                StringConversion stringConversion = field.getAnnotation(StringConversion.class);
                if(Objects.nonNull(stringConversion)){
                    //通过设置私有属性可被访问来重设值
                    field.setAccessible(true);
                    if(field.getType().equals(String.class)){
                        try {
                            String trim = stringConversion.trim();
                            String assignmentField = stringConversion.assignmentField();
                            String targetValue = stringConversion.targetValue();
                            String conversion = (String)field.get(o);
                            if(Objects.isNull(conversion)){
                                continue;
                            }
                            //获取属性名称
                            String fieldName = field.getName();
                            String targetField;
                            List<String> list = Arrays.stream(conversion.split(trim)).map(String::trim).collect(Collectors.toList());
                            if(!"".equals(targetValue)){
                                if(list.contains(targetValue)){
                                    String[] value = stringConversion.addValueByTarget();
                                    List<String> asList = Arrays.asList(value);
                                    if(!asList.isEmpty()){
                                        list.addAll(asList);
                                    }
                                }
                            }
                            //结果赋值
                            List<Object> objects = new ArrayList<>(list);
                            //赋值目标字段
                            if("".equals(assignmentField)){
                                targetField = fieldName+"List";
                            }else {
                                targetField = assignmentField;
                            }

                            for(Field f : fields){
                                if(f.getName().equals(targetField)){
                                    f.setAccessible(true);
                                    // 集合List元素,字段类型转换
                                    if (f.getType().equals(List.class)) {
                                        // 当前集合的泛型类型
                                        Type genericType = f.getGenericType();
                                        if (genericType instanceof ParameterizedType) {
                                            ParameterizedType pt = (ParameterizedType) genericType;
                                            // 得到泛型里的class类型对象
                                            Class<?> actualTypeArgument = (Class<?>)pt.getActualTypeArguments()[0];
                                            //long
                                            if(actualTypeArgument.equals(Long.class)){
                                                objects = list.stream().map(Long::valueOf).collect(Collectors.toList());
                                            }
                                            //int
                                            if(actualTypeArgument.equals(Integer.class)){
                                                objects = list.stream().map(Integer::valueOf).collect(Collectors.toList());
                                            }
                                            //BigDecimal
                                            if(actualTypeArgument.equals(BigDecimal.class)){
                                                objects = list.stream().map(BigDecimal::new).collect(Collectors.toList());
                                            }
                                        }
                                    }
                                    //最终赋值
                                    Object tranList = f.get(o);
                                    if(Objects.isNull(tranList)){
                                        f.set(o,objects);
                                    }
                                    break;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            log.error("转换失败:",e);
                        }
                    }
                }
            }
        }
        Object p = null;
        try{
            p= joinPoint.proceed();
        }catch (Throwable  e){
            e.printStackTrace();
        }
        return p;
    }

    public  List<Field> getAllFieldsList(final Class<?> cls) {
        List<Field> allFields = new ArrayList();

        for(Class currentClass = cls; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            Field[] var4 = declaredFields;
            int var5 = declaredFields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                allFields.add(field);
            }
        }

        return allFields;
    }
}
