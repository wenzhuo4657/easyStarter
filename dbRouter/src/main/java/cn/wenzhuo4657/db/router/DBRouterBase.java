package cn.wenzhuo4657.db.router;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 表路由，
 */
public class DBRouterBase {
    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }
}
