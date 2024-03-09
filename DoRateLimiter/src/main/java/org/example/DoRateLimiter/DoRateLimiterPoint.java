package org.example.DoRateLimiter;


import com.google.common.base.Throwables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.DoRateLimiter.annotation.DoRateLimiter;
import org.example.DoRateLimiter.valve.IValveService;
import org.example.DoRateLimiter.valve.impl.RateLimiterValve;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DoRateLimiterPoint {


    @Pointcut("@annotation(org.example.DoRateLimiter.annotation.DoRateLimiter)")
    public  void aopPoint(){
    }


    @Around("aopPoint() && @annotation(doRateLimiter)")
    public  Object doRouter(ProceedingJoinPoint point, DoRateLimiter doRateLimiter) throws Throwable {
        IValveService service= new RateLimiterValve();

        return  service.access(point,getMethod(point),doRateLimiter, point.getArgs());

    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
