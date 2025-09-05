package org.wenzhuo4657.gateway.annotation;

import org.springframework.context.annotation.Import;
import org.wenzhuo4657.gateway.autoConfigure.ServicesAutoConfigure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServicesAutoConfigure.class)
public @interface EnableGatewayClient {
/**
 * 通过import导入配置来启用配置，
 */
}
