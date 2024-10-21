package cn.wenzhuo4657.db.router;

import cn.wenzhuo4657.db.router.annotation.DBRouter;
import cn.wenzhuo4657.db.router.strategy.IDBRouterStrategy;
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
import org.springframework.stereotype.Component;

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

    private DBRouterConfig dbRouterConfig;


    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfig dbRouterConfig, IDBRouterStrategy dbRouterStrategy) {
        this.dbRouterConfig = dbRouterConfig;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    @Pointcut("@annotation(cn.wenzhuo4657.db.router.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint()&& @annotation(dbRouter)")
    public Object dbRouter(ProceedingJoinPoint point,DBRouter dbRouter) throws Throwable {

        Method method=getMethod(point);
        String dbKey = dbRouter.key();
        if (StringUtils.isBlank(dbKey)&&StringUtils.isBlank(dbRouterConfig.getRouterKey())){
            throw new RuntimeException("annotation DBRouter key is null！");
        }
        /**
         *  @author:wenzhuo4657
            des: 注解上的路由字段>配置上的路由字段
        */
        dbKey=StringUtils.isBlank(dbKey)?dbKey:dbRouterConfig.getRouterKey();


//        计算路由
          //  wenzhuo TODO 2024/10/21 : 参数列表中要有路由字段为变量名的变量？
        String dbKeyAttr=getAttrValue(dbKey, point.getArgs());
        dbRouterStrategy.doRouter(dbKeyAttr);
        try {
            return point.proceed();
        } finally {
            dbRouterStrategy.clear();
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

    /**
     *  @author:wenzhuo4657
        des:
     1，参数列表只有一个参数，且其类型为String时，直接返回其值。
     2.参数列表有多个，将每一个参数都看作bean，使用BeanUtils.getProperty(arg,attr)获取属性值
    */
    public String getAttrValue(String attr,Object[] args) {
        if (args.length==1){
            Object arg=args[0];
            /**
             *  @author:wenzhuo4657
                des:
             instanceof关键字：测试一个对象是否属于某个特定的类或接口，或者该类的任何一个子类。
             由于String类有final关键字，不可被继承，所以该关键字可以判断该类的实际类型是否是String类型。


             ps:对于java对象来说，对象具有引用类型和实际类型两种类型，引用类型实际上表示怎样读取实际类型，这也是java多态的基础。
             而instanceof关键字来说，它检测的是对象的实际类型，而并非引用类型。
            */
            if (arg instanceof String){
                return arg.toString();
            }
        }
        String filedValue=null;
        for (Object arg:args){
            try {
                if (StringUtils.isNotBlank(filedValue)){
                    break;
                }
                filedValue= BeanUtils.getProperty(arg,attr);
            }  catch (Exception e) {
                logger.error("获取路由属性值失败 attr：{}", attr, e);
            }
        }

        return filedValue;
    }





}
