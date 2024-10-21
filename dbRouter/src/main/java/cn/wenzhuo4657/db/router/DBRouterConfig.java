package cn.wenzhuo4657.db.router;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 路由配置
 */
public class DBRouterConfig {

    private int dbCount;  //分库数
    private int tbCount;  //分表数
    private String routerKey;//路由字段
    public DBRouterConfig() {
    }

    public DBRouterConfig(int dbCount, int tbCount) {
        this.dbCount = dbCount;
        this.tbCount = tbCount;
    }

    public DBRouterConfig(int dbCount, int tbCount, String routerKey) {
        this.dbCount=dbCount;
        this.tbCount=tbCount;
        this.routerKey=routerKey;
    }

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }
}
