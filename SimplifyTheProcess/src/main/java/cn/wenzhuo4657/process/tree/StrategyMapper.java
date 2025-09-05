package cn.wenzhuo4657.process.tree;
/**
 *    策略查找接口
 * @param <T>  入参类型
 * @param <D>  上下文类型
 * @param <R>  返回值类型
 */

public interface StrategyMapper <T, D, R>{


    /**
     * 获取策略处理器。如果返回null，则表示没有匹配的策略处理器，即处理结束，返回R对象
     * @param requestParameter  入参
     * @param dynamicContext  上下文
     * @return
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;
}
