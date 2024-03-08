package org.example.hystrix.valve.impl;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.example.hystrix.annotation.DoHystrix;
import org.example.hystrix.valve.IValveStrvice;

import java.lang.reflect.Method;

public class HystrixValveImpl extends HystrixCommand<Object> implements IValveStrvice {
    private ProceedingJoinPoint point;
    private Method method;
    private DoHystrix doHystrix;

    public  HystrixValveImpl(){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hystrix_01"))//命令组名称
                .andCommandKey(HystrixCommandKey.Factory.asKey("HyCommand_one"))//该命令名称
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("hystrix"))//线程池名称
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().
                        withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))//命令的相关配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10))//线程池相关配置
        );
    }

    @Override
    public Object access(ProceedingJoinPoint point, Method method, DoHystrix doHystrix, Object[] args) {
        this.point=point;
        this.method=method;
        this.doHystrix=doHystrix;

        // 设置熔断超时时间

        Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hystrix_01"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(doHystrix.timeoutValue()));

        //执行此类下小重写的run(),如果没有抛出错误则正常返回，反之，则执行其下重写的getFallback();
        return this.execute();
    }

//    返回方法调用的正确结果

    @Override
    protected Object run() throws Exception {
        try {
            return  point.proceed();
        }catch (Throwable throwable){
            return null;
        }

    }


//    返回熔断保护时候的保护信息

    @Override
    protected Object getFallback() {
        return JSON.parseObject(doHystrix.returnJson(),method.getReturnType());
    }
}
