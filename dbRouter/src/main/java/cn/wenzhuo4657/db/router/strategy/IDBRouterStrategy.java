package cn.wenzhuo4657.db.router.strategy;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/21
 * @description: 路由策略接口定义
 */
public interface IDBRouterStrategy {

    /**
     *  @author:wenzhuo4657
        des: 生成路由并更新到DBContextHolder中
    */
    void doRouter(String dbKeyAttr);

    /**
     *  @author:wenzhuo4657
        des: 清空DBContextHolder当前线程路由信息。
    */
    void clear();
}
