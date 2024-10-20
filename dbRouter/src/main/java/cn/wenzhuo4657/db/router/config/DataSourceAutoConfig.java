package cn.wenzhuo4657.db.router.config;

import cn.wenzhuo4657.db.router.Contants.AppEnum;
import cn.wenzhuo4657.db.router.DBRouterConfig;
import cn.wenzhuo4657.db.router.DBRouterJoinPoint;
import cn.wenzhuo4657.db.router.dynamic.DynamicDataSource;
import cn.wenzhuo4657.db.router.util.PropertyUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 数据源自动装配
 */
@Configuration
public class DataSourceAutoConfig implements EnvironmentAware {


    /**
     *  @author:wenzhuo4657
        des: 数据源配置组
    */
    private Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    /**
     *  @author:wenzhuo4657
        des: 默认数据源配置
    */
    private Map<String, Object> defaultDataSourceConfig;
    private int dbCount;  //分库数
    private int tbCount;  //分表数



    @Bean(name = "db-router-point")
    @ConditionalOnMissingBean //根据条件判断进行注入，默认条件为是否有相同的bean存在
    public DBRouterJoinPoint point(){
        return new DBRouterJoinPoint();
    }

    @Bean
    public DBRouterConfig dbRouterConfig() {
        return new DBRouterConfig(dbCount, tbCount);
    }

    @Bean
    public DataSource dataSource() {

        Map<Object, Object> targetDataSources = new HashMap<>();
        for (String dbInfo: dataSourceMap.keySet()){
            Map<String, Object> objMap = dataSourceMap.get(dbInfo);
            String url = objMap.get("url").toString();
            String username = objMap.get("username").toString();
            String password = objMap.get("password").toString();
            targetDataSources.put(dbInfo,getDriverManagerDataSource(url,username,password));
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "router.jdbc.datasource.";
        dbCount = Integer.valueOf(environment.getProperty(prefix + "dbCount"));
        tbCount = Integer.valueOf(environment.getProperty(prefix + "tbCount"));
        String dataSources = environment.getProperty(prefix + "list");

        for (String dbInfo:dataSources.split(AppEnum.DataSourse_name_split.getCode())){
            Map<String,Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dbInfo, Map.class);
            dataSourceMap.put(dbInfo, dataSourceProps);

        }
    }

    public DriverManagerDataSource getDriverManagerDataSource(String url,String username,String password){
        return new DriverManagerDataSource(url,username,password);
    }
}
