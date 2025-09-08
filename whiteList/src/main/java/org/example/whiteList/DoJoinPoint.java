package org.example.whiteList;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import jakarta.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.whiteList.annotation.DoWhiteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(value = 2)
public class DoJoinPoint {

    private Logger logger= LoggerFactory.getLogger(DoJoinPoint.class);
    @Resource//优先根据字段名称进行注入 Autowired优先根据字段类型注入
    @Qualifier("WhiteListConfig")//指定字段名称进行注入
    private  String whiteListConfig;


    @Pointcut("@annotation(org.example.whiteList.annotation.DoWhiteList)")
    public void aoppoint(){
    }


    @Around("aoppoint()")
    public Object doRouter(ProceedingJoinPoint point) throws InstantiationException, IllegalAccessException {
        Method method=getMethod(point);
        DoWhiteList whiteList=method.getAnnotation(DoWhiteList.class);
        String keyvalue=getFiledValue(whiteList.key(),point.getArgs());
        logger.info("middleware whitelist handler method：{} value：{}", method.getName(), keyvalue);
        if (null == keyvalue || "".equals(keyvalue)) {
            try {
                return point.proceed();
            } catch (Throwable e) {
                logger.info("异常：{}",e);
            }
        }

        String[] split = whiteListConfig.split(",");
        for (String str : split) {
            if (keyvalue.equals(str)) {
                try {
                    return point.proceed();
                } catch (Throwable e) {
                    logger.info("异常：{}",e);
                }
            }
        }

        return  returnObject(whiteList, method);
    }
    private  Method getMethod(ProceedingJoinPoint jp){
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        try {
            return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
             String E=Throwables.getStackTraceAsString(e);
             logger.info("异常：{}",E);
             return  null;
        }
    }

    private  String getFiledValue(String filedName,Object[] args){
        String filedValue=null;
        for (Object arg :args){
            if (null==filedValue||"".equals(filedValue)){
                try {
                    filedValue= BeanUtils.getProperty(arg,filedName);
                } catch (Exception e) {
                    if (args.length == 1) {
                        return args[0].toString();
                    }else{
                        String E=Throwables.getStackTraceAsString(e);
                        logger.info("异常：{}",E);
                    }
                }
            }else {
                break;
            }

        }

        return  filedName;
    }


    private  Object returnObject(DoWhiteList whiteList, Method method) throws InstantiationException, IllegalAccessException {
        Class<?> returnType=method.getReturnType();
        String returnJson=whiteList.returnJson();

        if ("".equals(returnJson)){
            return  returnType.newInstance();
        }

        return JSON.parseObject(returnJson,returnType);



    }
}
