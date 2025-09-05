package org.wenzhuo4657.gateway.autoConfigure;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Slf4j
@Order(2)
@Configuration
//通过配置启用服务模块的配置，默认关闭
@ConditionalOnProperty(prefix = "gateway.services", name = "enable", havingValue = "true", matchIfMissing = false)
public class ServicesAutoConfigure {

    public ServicesAutoConfigure() {
        log.info("ServicesAutoConfigure初始化-----");
    }




}
