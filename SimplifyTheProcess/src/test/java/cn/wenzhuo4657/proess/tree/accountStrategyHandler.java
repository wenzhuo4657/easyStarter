package cn.wenzhuo4657.proess.tree;

import cn.wenzhuo4657.process.tree.StrategyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 并不是所有的处理器都需要实现StrategyMapper进行路由，某些时候我们可能仅仅需要一个叶子节点。
 * <pre>
 *           +---------+
 *           |  Root   |   ----------- 第 1 层策略入口
 *           +---------+
 *            /       \  ------------- 根据入参 P1 进行策略分发
 *           /         \
 *     +------+      +------+
 *     |  A   |      |  B   |  ------- 第 2 层不同策略的实现
 *     +------+      +------+
 *       /  \          /  \  --------- 根据入参 P2 进行策略分发
 *      /    \        /    \
 *   +---+  +---+  +---+  +---+
 *   |A1 |  |A2 |  |B1 |  |B2 |  ----- 第 3 层不同策略的实现
 *   +---+  +---+  +---+  +---+
 * </pre>
 *
 * 具体来说apply方法是否涵盖router方法，即代表是否需要向下处理，我们也可以设计一个逻辑既可以向下处理也可以直接返回。
 * 例如：
 * ```
 * if(true) {
 *     return router(requestParameter, dynamicContext);  // 这里也可以决定是否直接返回路由处理的结果
 * }else{
 *     ......
 *     return xxxx；
 * }
 *
 *
 * ```
 */
public class accountStrategyHandler implements StrategyHandler {

    private static final Logger log = LoggerFactory.getLogger(accountStrategyHandler.class);

    @Override
    public Object apply(Object requestParameter, Object dynamicContext) throws Exception {
        log.info("accountStrategyHandler apply");
        log.info("accountStrategyHandler apply requestParameter: {}", requestParameter);
        log.info("accountStrategyHandler apply dynamicContext: {}", dynamicContext);
        log.info("accountStrategyHandler apply result: {}", requestParameter);
        return requestParameter;
    }
}
