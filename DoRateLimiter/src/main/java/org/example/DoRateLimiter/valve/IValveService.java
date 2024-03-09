package org.example.DoRateLimiter.valve;

import org.aspectj.lang.ProceedingJoinPoint;
import org.example.DoRateLimiter.annotation.DoRateLimiter;

import java.lang.reflect.Method;

public interface IValveService {

    Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable;
}
