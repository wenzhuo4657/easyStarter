package cn.wenzhuo4657.process.link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标记不具备处理问题能力的节点,通常作为责任链的入口
 */
public    class NoSupport<T extends Trouble> extends  Support<T> {


    private static final Logger log = LoggerFactory.getLogger(NoSupport.class);

    protected NoSupport(String name) {
        super(name,false);
    }

    @Override
    public boolean resolve(T trouble) {
        return true;
    }

    @Override
    public boolean handle(T trouble) {
        log.info("handle开始处理节点");
        return false;
    }
}
