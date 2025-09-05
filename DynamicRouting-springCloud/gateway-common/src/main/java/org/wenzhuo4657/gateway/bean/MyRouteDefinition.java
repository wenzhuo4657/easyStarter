package org.wenzhuo4657.gateway.bean;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.io.Serializable;
import java.util.HashMap;

public class MyRouteDefinition extends RouteDefinition implements Serializable {

    public String route_name;

}
