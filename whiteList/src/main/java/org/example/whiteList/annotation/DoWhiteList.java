package org.example.whiteList.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited//是该注解具有继承性，无需在子类中重复声明
public @interface DoWhiteList {
    String key() default "";
    String returnJson() default  "";
}
