package cn.wenzhuo4657.process.tree;


/**
 *   处理器接口定义
 * @param <T>  入参类型
 * @param <D>  上下文类型
 * @param <R>  返回值类型
 */
public interface StrategyHandler<T,D,R> {


    StrategyHandler DEFAULT = (T, D) -> null;
    /**
     * 处理器方法
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;
}
