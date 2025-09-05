package org.wenzhuo4657.gateway.annotation;


import org.springframework.context.annotation.Import;
import org.wenzhuo4657.gateway.autoConfigure.GatewayAutoConfigure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GatewayAutoConfigure.class)
public @interface EnableGatewayService {
}
