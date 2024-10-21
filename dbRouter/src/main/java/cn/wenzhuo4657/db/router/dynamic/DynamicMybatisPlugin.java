package cn.wenzhuo4657.db.router.dynamic;

import cn.wenzhuo4657.db.router.DBContextHolder;
import cn.wenzhuo4657.db.router.annotation.DBRouterStrategy;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description:  sql拦截(mybatis)，
 *
 * 参考：http://www.mybatis.cn/archives/733.html
 * （1）ParameterHandler：处理SQL的参数对象
 * （2）ResultSetHandler：处理SQL的返回结果集
 * （3）StatementHandler：数据库的处理对象，用于执行SQL语句
 * （4）Executor：MyBatis的执行器，用于执行增删改查操作
 *
 */

@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class, Intercepts.class})})
public class DynamicMybatisPlugin  implements Interceptor {

    private Logger log= LoggerFactory.getLogger(DynamicMybatisPlugin.class);

    /**
     *  @author:wenzhuo4657
        des: 正则表达式，用于捕获sql语句的表名。
    */
    private Pattern pattern=Pattern.compile("(from|into|update)[\\s]{1,}(\\w{1,})",Pattern.CASE_INSENSITIVE);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler target = (StatementHandler) invocation.getTarget();
        MetaObject metaObject=
                        MetaObject.forObject(target, SystemMetaObject.DEFAULT_OBJECT_FACTORY,SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");


        String id= mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf('.'));
        Class<?> aClass = Class.forName(className);

//        获取类注解,进行截断判断
        DBRouterStrategy annotation = aClass.getAnnotation(DBRouterStrategy.class);
        if (Objects.isNull(annotation)||!annotation.splitTable()){
            return invocation.proceed();
        }


//        获取sql
        BoundSql boundSql = target.getBoundSql();
        String sql = boundSql.getSql();
        log.info("mybatis-sql拦截前:{}",sql);

//        修改sql语句: 替换表名
        Matcher matcher= pattern.matcher(sql);
        String tableName=null;
        if (matcher.find()){
            tableName=matcher.group().trim();
        }
        assert null != tableName;
        String replaceAll = matcher.replaceAll(tableName + "_" + DBContextHolder.getTBKey());

        log.info("mybatis-sql拦截中： \nBoundSql:{} \n target:{} ", JSON.toJSONString(boundSql),replaceAll);

//        反射修改字节码文件
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql,replaceAll);
  //  wenzhuo TODO 2024/10/21 : 不是很能理解这里的反射逻辑，反射代码究竟在什么时候执行，该方法仅仅修改当前对象的字节码文件？
        log.info("mybatis-sql拦截后： \nBoundSql:{}  ", JSON.toJSONString(target.getBoundSql()));

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
