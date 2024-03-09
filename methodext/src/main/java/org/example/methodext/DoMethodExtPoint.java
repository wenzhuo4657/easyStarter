package org.example.methodext;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.methodext.annotation.DoMethodExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Aspect
@Component
@Order(value = 1)
public class DoMethodExtPoint {
    private Logger logger= LoggerFactory.getLogger(DoMethodExtPoint.class);

    @Pointcut("@annotation(org.example.methodext.annotation.DoMethodExt)")
    public  void aopPoint(){}


//    执行自定义的拦截方法，并根据其拦截方法的返回判定是否继续执行原方法
    /*
    * 拦截方法的约束
    * 返回值为布尔类型
    * 参数列表与原方法参数列表相同
    * */

    @Around("aopPoint()")
    public  Object doRouter(ProceedingJoinPoint Point) throws Throwable {
        Method method=GET_Method(Point);
        DoMethodExt doMethodExt=method.getAnnotation(DoMethodExt.class);
        String methodName= doMethodExt.method();

        Method methodExt=getClass(Point).getMethod(methodName,method.getParameterTypes());
        Class<?> returnType = methodExt.getReturnType();


        if (!returnType.getName().equals("boolean")){
            throw new RuntimeException("annotation @DoMethodExt set method：" + methodName + " returnType is not boolean");
        }

        // 拦截判断正常，继续
        boolean invoke = (boolean) methodExt.invoke(Point.getThis(), Point.getArgs());




        return invoke?Point.proceed(): JSON.parseObject(doMethodExt.returnJson(),method.getReturnType());
    }


    private Method GET_Method(ProceedingJoinPoint point) throws NoSuchMethodException {
        Signature sig=point.getSignature();
        MethodSignature methodSignature=(MethodSignature) sig;
        return  point.getTarget().getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());
    }

    private  Class<?extends  Object> getClass(JoinPoint point){
        return  point.getTarget().getClass();
    }

}
