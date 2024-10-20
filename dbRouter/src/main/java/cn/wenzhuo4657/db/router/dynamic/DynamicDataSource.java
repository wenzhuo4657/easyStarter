package cn.wenzhuo4657.db.router.dynamic;

import cn.wenzhuo4657.db.router.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "db"+ DBContextHolder.getDBKey();
    }
}
