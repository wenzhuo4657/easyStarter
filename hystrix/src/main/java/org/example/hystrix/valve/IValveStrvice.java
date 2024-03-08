package org.example.hystrix.valve;

import org.aspectj.lang.ProceedingJoinPoint;
import org.example.hystrix.annotation.DoHystrix;

import java.lang.reflect.Method;

public interface IValveStrvice {
        Object access(ProceedingJoinPoint point, Method method, DoHystrix doHystrix,Object[] args);
}
