package cn.wenzhuo4657.process.tree.node;

import cn.wenzhuo4657.process.tree.AbstractMultiThreadStrategyRouter;
import cn.wenzhuo4657.process.tree.AbstractStrategyRouter;
import cn.wenzhuo4657.process.tree.StrategyHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 根节点模板，代表责任树的执行入口
 * todo 想要做一个可以展示树结构的方法，类似于打印出一张图
 */
public abstract  class RootNode  <T,D,R> extends AbstractStrategyRouter<T,D,R> {

    Logger logger;

    public RootNode(Logger  log) {
        this.logger = log;
    }



    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        logger.info("规则决策树开始执行 :{}, 入参: {}", logger.getName(), requestParameter);
        return router(requestParameter, dynamicContext);
    }


    @Override
    public StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception {
        throw  new RuntimeException("规则决策树根节点没有重写get方法，无法执行！！！");
    }




}
