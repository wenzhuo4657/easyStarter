package org.example.whiteList.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WhiteListProperties.class)
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigure {


    @Bean("WhiteListConfig")
    @ConditionalOnMissingBean
    public  String whiteListConfig(WhiteListProperties properties){
        return properties.getUser();
    }

//    @Bean("DoJoinPoint")
//    @ConditionalOnMissingBean
//    public  DoJoinPoint DoJoinPoint(){
//        return  new DoJoinPoint();
//    }
}
