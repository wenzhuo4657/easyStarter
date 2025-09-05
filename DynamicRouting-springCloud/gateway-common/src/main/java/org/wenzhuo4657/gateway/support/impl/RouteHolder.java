package org.wenzhuo4657.gateway.support.impl;



import lombok.Data;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.wenzhuo4657.gateway.bean.MyRouteDefinition;
import org.wenzhuo4657.gateway.support.IRouteHolder;


/**
 * 路由条目缓存，用于隔离dao层/redis客户端层，
 */

@Data
public class RouteHolder  implements IRouteHolder {




    String  key="Router_id";
//    todo 此处目前使用的是长链接，实时高，但是并没有起到隔离层的效果，待修改
    RMap<String, MyRouteDefinition> map;


    private RedissonClient redissonClient;

    public RouteHolder(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        map = redissonClient.getMap(key);
    }

    @Override
    public MyRouteDefinition getById(String id){
        return map.get(id);
    }


    @Override
    public void  set(String id, MyRouteDefinition routeDefinition){
        map.put(id,routeDefinition);
    }

}

