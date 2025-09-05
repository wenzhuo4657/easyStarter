package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.wenzhuo4657.gateway.annotation.EnableGatewayClient;
import org.wenzhuo4657.gateway.bean.MyRouteDefinition;
import org.wenzhuo4657.gateway.event.ServicesLoginEvent;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@EnableGatewayClient
@SpringBootApplication
public class Main implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Autowired
    private ServicesLoginEvent servicesLoginEvent;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServicesLoginEvent.ServicsLogin servicsLogin = new ServicesLoginEvent.ServicsLogin();
        servicsLogin.name = "test";
        servicsLogin.route_id = "test";
        MyRouteDefinition myRouteDefinition = new MyRouteDefinition();
        myRouteDefinition.route_name = "test";

        myRouteDefinition.setId(myRouteDefinition.route_name);
        myRouteDefinition.setUri(URI.create("http://localhost:8080"));


        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("pattern", "/api/test/**");
        predicateDefinition.setArgs(objectObjectHashMap);
        myRouteDefinition.setPredicates(List.of(predicateDefinition));

        FilterDefinition filterDefinition=new FilterDefinition();
        filterDefinition.setName("RewritePath");
        HashMap<String, String> objectObjectHashMap1 = new HashMap<>();
        objectObjectHashMap1.put("regexp", "/api/(?<segment>.*)");
        objectObjectHashMap1.put("replacement", "/${segment}");
        filterDefinition.setArgs(objectObjectHashMap1);
        myRouteDefinition.setFilters(List.of(filterDefinition));
        servicesLoginEvent.Login(servicsLogin,myRouteDefinition);
    }
}