package cn.wenzhuo4657.process.tree;

/**
 *    路由流程定义抽象类
 */
public abstract class AbstractStrategyRouterr<T, D, R> implements StrategyHandler<T, D, R>, StrategyMapper<T, D, R> {


    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;
    /**
     * 获取路由处理
     * @param requestParameter  入参
     * @param dynamicContext  上下文
     * @return
     * @throws Exception
     */
    public R router(T requestParameter, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        if(null != strategyHandler) return strategyHandler.apply(requestParameter, dynamicContext);
        return defaultStrategyHandler.apply(requestParameter, dynamicContext);
    }
}
