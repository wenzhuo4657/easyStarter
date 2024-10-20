package cn.wenzhuo4657.db.router.config;

import cn.wenzhuo4657.db.router.Contants.AppEnum;
import cn.wenzhuo4657.db.router.DBRouterConfig;
import cn.wenzhuo4657.db.router.dynamic.DynamicDataSource;
import cn.wenzhuo4657.db.router.util.PropertyUtil;
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


    private Map<String, Map<String, Object>> dataSourceMap = new HashMap<>();

    private int dbCount;  //分库数
    private int tbCount;  //分表数

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
        String dataSources = environment.getProperty(prefix + "list");
        /**
         *  @author:wenzhuo4657
            des: assert断言关键字，1.4之后引入，默认禁用，通过 vm参数 -ea启用
         作用：根据布尔表达式判断结果抛出错误，进行快速检查，生产环境中不建议使用
        */
        assert dataSources!=null;
        for (String each:dataSources.split(AppEnum.DataSourse_name_split.getCode())){
              //  wenzhuo TODO 2024/10/20 : 不是很理解为什么要注入到map中，
            PropertyUtil.handle(environment,prefix+each,Map.class);
        }
    }

    public DriverManagerDataSource getDriverManagerDataSource(String url,String username,String password){
        return new DriverManagerDataSource(url,username,password);
    }
}
