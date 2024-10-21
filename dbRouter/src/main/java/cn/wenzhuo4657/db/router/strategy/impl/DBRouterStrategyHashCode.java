package cn.wenzhuo4657.db.router.strategy.impl;

import cn.wenzhuo4657.db.router.DBContextHolder;
import cn.wenzhuo4657.db.router.DBRouterConfig;
import cn.wenzhuo4657.db.router.strategy.IDBRouterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 哈希实现
 */
public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private Logger log= LoggerFactory.getLogger(DBRouterStrategyHashCode.class);
    private DBRouterConfig dbRouterConfig;

    public DBRouterStrategyHashCode(DBRouterConfig dbRouterConfig) {
        this.dbRouterConfig = dbRouterConfig;
    }

    @Override
    public void doRouter(String dbKeyAttr) {
        int size = dbRouterConfig.getDbCount() * dbRouterConfig.getTbCount();
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));
        int dbIdx = idx / dbRouterConfig.getTbCount() + 1;
        int tbIdx = idx - dbRouterConfig.getTbCount() * (dbIdx - 1);
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));
        log.debug("数据库路由 dbIdx：{} tbIdx：{}",  dbIdx, tbIdx);

    }

    @Override
    public void clear() {
        DBContextHolder.clearDBKey();
        DBContextHolder.clearTBKey();
    }
}
