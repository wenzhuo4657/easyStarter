package org.wenzhuo4657.gateway.event;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.wenzhuo4657.gateway.support.impl.RouteHolder;
import org.wenzhuo4657.gateway.bean.MyRouteDefinition;

/**
 * 该类用语定义模块上下的通知模式
* */
@Slf4j
public class ServicesLoginEvent {

//     1， 发送、监听服务上线消息 2， 缓存、接受路由定义，

    private  String Tpoic="ServicesLoginEvent";

    private RedissonClient redissonClient;

    public static RouteHolder routeHolder;//   模块端专用

    private  ApplicationEventPublisher publisher;



    public ServicesLoginEvent(ApplicationEventPublisher publisher,  RedissonClient redissonClient,RouteHolder routeHolder) {

        this.routeHolder=routeHolder;
        this.publisher=publisher;
        this.redissonClient=redissonClient;


    }


//    网关端
    public void setListenter()
    {
        //        监听消息，发送刷新事件
        redissonClient.getTopic(Tpoic).addListener(ServicsLogin.class, (channel, message) -> {
            log.info("接收到消息：" + message.name);

            publisher.publishEvent(new RefreshRoutesEvent(this));//发布刷新事件

        });

    }



//    服务端
    public void Login(ServicsLogin message, MyRouteDefinition routeDefinition)
    {

        //    1,发送topic通知服务上线、服务路由定义添加
        redissonClient.getTopic(Tpoic).publish(message);

        //     2，缓存路由定义
        routeHolder.set(message.route_id,routeDefinition);

    }


    public static class  ServicsLogin{
        public String name;
        public String route_id;
    }
}
