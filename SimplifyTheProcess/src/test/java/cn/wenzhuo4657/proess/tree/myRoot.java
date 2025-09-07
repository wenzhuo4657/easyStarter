package cn.wenzhuo4657.proess.tree;

import cn.wenzhuo4657.process.tree.StrategyHandler;
import cn.wenzhuo4657.process.tree.node.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class myRoot extends RootNode {
    static Logger log = LoggerFactory.getLogger(myRoot.class);
    public myRoot() {
        super(log);
    }

    @Override
    public StrategyHandler get(Object requestParameter, Object dynamicContext) throws Exception {
        return new accountStrategyHandler();
    }
}
