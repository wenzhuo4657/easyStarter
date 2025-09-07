package cn.wenzhuo4657.process.tree;
/**
 *    策略查找接口
 * @param <T>  入参类型
 * @param <D>  上下文类型
 * @param <R>  返回值类型
 */

public interface StrategyMapper <T, D, R>{


    /**
     * 策略处理器分发
     * @param requestParameter  入参
     * @param dynamicContext  上下文
     * @return
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;
}
