package org.wenzhuo4657.gateway.autoConfigure;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.wenzhuo4657.gateway.support.impl.RouteHolder;
import org.wenzhuo4657.gateway.event.ServicesLoginEvent;
import org.wenzhuo4657.gateway.repository.RedisRouteDefinitionRepository;

@Order(2)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "gateway", name = "enable", havingValue = "true", matchIfMissing = false)// 手动启动
public class GatewayAutoConfigure {



    private ServicesLoginEvent servicesLoginEvent;
    public GatewayAutoConfigure(ServicesLoginEvent servicesLoginEvent) {

        this.servicesLoginEvent = servicesLoginEvent;
        log.info("GatewayAutoConfigure初始化-------");
        servicesLoginEvent.setListenter();
    }




    @Bean
    public RedisRouteDefinitionRepository redisRouteDefinitionRepository(RedissonClient redissonClient, RouteHolder routeHolder){
        return  new RedisRouteDefinitionRepository(routeHolder);
    }

}
