# spring-boot-starter

这是一个基于 Spring Boot 3.0+ 的自动装配 starter，利用新的 `Imports` 方式替换传统的 `spring.factories` 机制（详情可见： [SpringBoot 3 替换 spring.factories](https://www.cnblogs.com/jingzh/p/18793407)）。

## 📦 功能模块

| 名称                     | 功能描述                                                     |
|------------------------|--------------------------------------------------------------|
| **DoRateLimiter**      | 基于注解的分布式限流组件。      |
| **Hystrix**            | 提供熔断式注解，开箱即用。        |
| **MethodExt**          | 方法返回值拦截器，判断返回值的数据类型和内容来决定是否返回    |
| **WhiteList**          | 方法/接口白名单机制，用于权限控制或特殊场景的请求放行         |
| **DynamicRoute**       | 动态路由管理组件，运行时模块动态上下线，无需管理重启网关   |
| **SimplifyTheProcess** | 责任链和规则树的代码实现，提取公共逻辑，便于快速开发|

## ✨ 特性

- **版本适配**：专为 Spring Boot 3.0+ 设计，采用最新 `Imports` 自动装配方案
- **轻量级**：按需引入模块，无侵入式依赖

## 🚀 快速开始


### maven全球仓库

全局统一的groupId为:<groupId>io.github.wenzhuo4657</groupId>
- [参照](https://github.com/wenzhuo4657?tab=packages&repo_name=easyStarter)

- bom工程管理
```
   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>io.github.wenzhuo4657</groupId>
               <artifactId>easyStarter-bom</artifactId>
               <version>1.0.0-SNAPSHOT</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   
```
