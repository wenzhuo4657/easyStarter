package cn.wenzhuo4657.db.router;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 路由配置
 */
public class DBRouterConfig {

    private int dbCount;  //分库数
    private int tbCount;  //分表数
    public DBRouterConfig() {
    }

    public DBRouterConfig(int dbCount, int tbCount) {
        this.dbCount = dbCount;
        this.tbCount = tbCount;
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
