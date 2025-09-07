package cn.wenzhuo4657.process.link;

/**
 * 节点
 */
public interface Node<T extends Trouble> {
    /**
     * 获取节点名称
     */
    String getName();
    /**
     * 设置推卸责任的节点
     */
    Node<T> setNext(Node<T> node);



    /**
     * 判断当前节点能否处理
     */
    boolean resolve(T trouble);


    /**
     * 处理问题
     */
    public  void apply(T trouble);


}


