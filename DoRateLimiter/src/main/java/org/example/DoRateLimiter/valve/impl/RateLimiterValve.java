package org.example.DoRateLimiter.valve.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import org.example.DoRateLimiter.Constants;
import org.example.DoRateLimiter.annotation.DoRateLimiter;
import org.example.DoRateLimiter.valve.IValveService;


import java.lang.reflect.Method;

public class RateLimiterValve implements IValveService {
    @Override
    public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {

        if (0==doRateLimiter.peritsPerSecond()){
            return jp.proceed();//判断是否开启限流保护
        }

        String ClassName=jp.getTarget().getClass().getName();
        String methodName=method.getName();
        String key=ClassName+":"+methodName;
        if (null == Constants.rateLimiterMap.get(key)) {
            Constants.rateLimiterMap.put(key, RateLimiter.create(doRateLimiter.peritsPerSecond()));
        }

        RateLimiter rateLimiter=Constants.rateLimiterMap.get(key);

        if (rateLimiter.tryAcquire()){
            return  jp.proceed();
        }



        return JSON.parseObject(doRateLimiter.returnJson(),method.getReturnType());
    }
}
