package org.wenzhuo4657.gateway.autoConfigure;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.wenzhuo4657.gateway.event.ServicesLoginEvent;
import org.wenzhuo4657.gateway.support.impl.RouteHolder;
import org.wenzhuo4657.gateway.config.RedisClientConfigProperties;


/**
 * 公共模块自动配置，通过imports文件自动导入
 */
@Order(value = 1)
@Configuration
@ConditionalOnClass(RedisClientConfigProperties.class)
@EnableConfigurationProperties(RedisClientConfigProperties.class)
public class AutoConfigure {

    @Bean("redissonClient")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(ConfigurableApplicationContext applicationContext, RedisClientConfigProperties configProperties){
        Config config=new Config();
        config.useSingleServer()
                .setAddress("redis://"+configProperties.getHost()+":"+configProperties.getPort())
                .setConnectionPoolSize(configProperties.getPoolSize())
                .setConnectionMinimumIdleSize(configProperties.getMinIdleSize())
//                                                .setUsername(configProperties.getUsername())
//                                                        .setPassword(configProperties.getPassword())
                .setDatabase(0);
//        config.setCodec(new Kryo5Codec());
        config.setCodec(JsonJacksonCodec.INSTANCE);
        return Redisson.create(config);
    }


    @Bean("servicesLoginEvent")
    public ServicesLoginEvent servicesLoginEvent(ApplicationEventPublisher publisher, RedissonClient redissonClient, RouteHolder routeHolder){
        return  new ServicesLoginEvent(publisher,redissonClient,routeHolder);
    }

    @Bean("RouteHolder")
    public RouteHolder routeHolder(RedissonClient redissonClient){
        return new RouteHolder(redissonClient);
    }
}
