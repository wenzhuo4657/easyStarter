package org.wenzhuo4657.gateway.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.wenzhuo4657.gateway.support.impl.RouteHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class RedisRouteDefinitionRepository  implements RouteDefinitionRepository {


    public   static RouteHolder routeHolder;   //gateway专用

    public RedisRouteDefinitionRepository(RouteHolder routeHolder) {

        this.routeHolder=routeHolder;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
//        该方法会在初始化时自动调用，并非服务端上线才会调用
        if (routeHolder.getMap()==null||routeHolder.getMap().size()==0){
            log.info("暂无可用服务");
            return Flux.empty();
        }
        log.info("重新加载路由条目");

        return Flux.fromIterable(routeHolder.getMap().values());


    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
