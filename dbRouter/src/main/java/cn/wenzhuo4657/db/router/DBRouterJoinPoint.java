package cn.wenzhuo4657.db.router;

import cn.wenzhuo4657.db.router.annotation.DBRouter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */
@Aspect
@Component("db-router-point")
public class DBRouterJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);
    @Autowired
    private DBRouterConfig dbRouterConfig;


    @Pointcut("@annotation(cn.wenzhuo4657.db.router.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint()&& @annotation(dbRouter)")
    public Object dbRouter(ProceedingJoinPoint point,DBRouter dbRouter) throws Throwable {
        Method method=getMethod(point);
        String dbKey = dbRouter.key();
        if (StringUtils.isBlank(dbKey)) throw new RuntimeException("annotation DBRouter key is null！");

//        计算路由
        String dbKeyAttr=getAttrValue(dbKey, point.getArgs());
        int size=dbRouterConfig.getDbCount()* dbRouterConfig.getTbCount();
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));

        int dbIdx = idx / dbRouterConfig.getTbCount() + 1;
        int tbIdx = idx - dbRouterConfig.getTbCount() * (dbIdx - 1);

        DBContextHolder.setDBKey(String.format("%02d",dbIdx));
        DBContextHolder.setTBKey(String.format("%02d",tbIdx));
        logger.info("数据库路由 method：{} dbIdx：{} tbIdx：{}", method.getName(), dbIdx, tbIdx);
        try {
            return point.proceed();
        } finally {
            DBContextHolder.clearDBKey();
            DBContextHolder.clearTBKey();
        }

    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        Class<?>[] interfaces = getClass(jp).getInterfaces();
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

    public String getAttrValue(String attr,Object[] args) {
        String filedValue=null;
        for (Object arg:args){
            try {
                if (StringUtils.isBlank(filedValue)){
                    filedValue= BeanUtils.getProperty(arg,attr);
                }else {
                    break;
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return filedValue;
    }





}
