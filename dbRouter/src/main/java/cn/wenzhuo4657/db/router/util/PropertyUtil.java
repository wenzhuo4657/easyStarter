package cn.wenzhuo4657.db.router.util;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/19
 * @description: 配置文件注入工具类。
 */
public class PropertyUtil {
    private static int springBootVersion = 1;
    static {
        try {
            /**
             *  @author:wenzhuo4657
                des: 通过反射加载加载特点类，来判断当前springboot的版本。
            当然，此处仅仅做了一个简单的判断。
            因为并没有考虑到springboot3,RelaxedPropertyResolver仅在1x版本中存在
            */
            Class.forName("org.springframework.boot.bind.RelaxedPropertyResolver");
        } catch (ClassNotFoundException e) {
            springBootVersion = 2;
        }

    }

    public static <T> T handle(final Environment environment,final String prefix,final Class<T> targetClass){
        switch (springBootVersion) {
            case 1:
                return (T) v1(environment, prefix);
            default:
                return (T) v2(environment, prefix, targetClass);
        }
    }

    private static Object v1(final Environment environment, final String prefix) {
          //  wenzhuo TODO 2024/10/19 :待编写，
        throw  new RuntimeException();
    }

    /**
     * @className: PropertyUtil
     * @author: wenzhuo4657
     * @date:  9:15
     * @Version: 2.0
     * @description:  springboot中用于绑定配置文件到目标类的方法
     * Environment： 表示当前应用程序运行环境的接口。对应用程序环境的两个关键方面进行建模： 配置文件 和 属性。属性访问相关的方法通过 PropertyResolver superinterface 暴露
     * prefix：要绑定的前缀
     * targetClass: 要绑定目标类的class对象。
     *
     * 注意：此处提示，springboot注入属性是通过反射获取set、get方法编译字节码文件进行修改。
     */

    private static Object v2(final Environment environment, final String prefix, final Class<?> targetClass) {
        try {



            Class<?> binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
            /**
             *  @author:wenzhuo4657
                des:
                Binder.get(Environment) :Binder从指定环境创建新实例。返回一个binder实例
                Binder.bind(String,Class): name – 要绑定的配置属性名称  target – 目标类。返回绑定结果，类型通过泛型变量编译为target变量的类型.
            */
            Method getMethod=binderClass.getDeclaredMethod("get", Environment.class);

            Method bindMethod=binderClass.getDeclaredMethod("bind",String.class, Class.class);
            /**
             *  @author:wenzhuo4657
                des: 由于get方法是静态方法，所以执行对象为null,
            */
            Binder binderObject= (Binder) getMethod.invoke(null, environment);

            String prefixParam = prefix.endsWith(".") ? prefix.substring(0, prefix.length() - 1) : prefix;

            Object bindResultObject = bindMethod.invoke(binderObject, prefixParam, targetClass);
          //  wenzhuo TODO 2024/10/20 : 不懂这里为什么能找到get方法
            Method resultGetMethod = bindResultObject.getClass().getDeclaredMethod("get");
            return resultGetMethod.invoke(bindResultObject);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }



}
