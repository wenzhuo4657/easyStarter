package org.wenzhuo4657.gateway.support;


import org.wenzhuo4657.gateway.bean.MyRouteDefinition;

/**
 * redis路由条目的交互定义,无论是服务端还是网关端，都对其隔离了真正的redis客户端，仅仅使用api
 * */
public interface IRouteHolder {


    /**
     *  根据id获取路由定义
     */

    public MyRouteDefinition getById(String id);


    /**
     *  设置路由定义
     */
    public void  set(String id, MyRouteDefinition routeDefinition);
}
