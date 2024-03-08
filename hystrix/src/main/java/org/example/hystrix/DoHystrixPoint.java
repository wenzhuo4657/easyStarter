package org.example.hystrix;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.hystrix.annotation.DoHystrix;
import org.example.hystrix.valve.IValveStrvice;
import org.example.hystrix.valve.impl.HystrixValveImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DoHystrixPoint {



    @Pointcut("@annotation(org.example.hystrix.annotation.DoHystrix)")
    public  void aopPoint(){
    }


    @Around("aopPoint()&&@annotation(doHystrix)")
    public  Object doRouter(ProceedingJoinPoint point, DoHystrix doHystrix) throws NoSuchMethodException {
        IValveStrvice valveService = new HystrixValveImpl();
        return valveService.access(point, getMethod(point), doHystrix, point.getArgs());

    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
