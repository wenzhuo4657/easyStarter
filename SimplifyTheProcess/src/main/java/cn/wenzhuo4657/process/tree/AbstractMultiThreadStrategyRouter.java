package cn.wenzhuo4657.process.tree;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 异步资源加载策略
 *
 * 弃用说明： 暂时未想到场景，请使用AbstractStrategyRouterr
 */
@Deprecated
public abstract  class AbstractMultiThreadStrategyRouter<T,D,R>  implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;



    /**
     * 路由模板方法
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    public R router(T requestParameter, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        if(null != strategyHandler) return strategyHandler.apply(requestParameter, dynamicContext);
        return defaultStrategyHandler.apply(requestParameter, dynamicContext);
    }


    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        // 异步加载数据
        multiThread(requestParameter, dynamicContext);
        // 业务流程受理
        return doApply(requestParameter, dynamicContext);
    }

    /**
     * 异步加载数据
     */
    protected abstract void multiThread(T requestParameter, D dynamicContext) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * 业务流程受理
     */
    protected abstract R doApply(T requestParameter, D dynamicContext) throws Exception;
}
