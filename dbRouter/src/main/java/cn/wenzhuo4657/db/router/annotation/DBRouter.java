package cn.wenzhuo4657.db.router.annotation;


import java.lang.annotation.*;

/**
 *  @author:wenzhuo4657
    des: 路由id标记
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {
    String key() default "";
}
