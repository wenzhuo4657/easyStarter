package cn.wenzhuo4657.db.router.annotation;


import java.lang.annotation.*;


/**
 *  @author:wenzhuo4657
    des: 路由表标记，是否开启路由注解
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DBRouterStrategy {
    boolean splitTable() default false;
}
