package cn.wenzhuo4657.process.link;

/**
 * 标记具备处理问题能力的节点
 */
public abstract class Support<T extends Trouble> implements  Node<T> {

    private String name;
    private Node<T> next;

    /**
     * 节点的预期处理结果，决定是否推卸责任
     *
     * true： 表示当前节点处理成功时，继续向下执行
     * false：表示当前节点处理失败时，继续向下执行
     *   * 处理方法：handle


     */
    private boolean expected= true;
    public Support(String name) {
        this.name = name;
    }

    public Support(String name, boolean expected) {
        this.name = name;
        this.expected = expected;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Node<T> setNext(Node<T> node) {
        this.next = node;
        return next;
    }


    @Override
    public boolean resolve(T trouble) {
        return true;
    }

    /**
     * 当前节点处理问题的方法
     * @param trouble
     */
    public abstract boolean handle(T trouble);


    /**
     * 节点处理问题: 根据预期处理结果判定是否继续处理
     *      * 处理结果判定：
     *      * 1. 节点可以处理问题并处理成功，表示true
     *      * 2，节点可以处理问题但处理失败，表示false
     *      * 3. 节点不能处理问题，表示false
     */
    public  void apply(T trouble) {

        boolean resolve = resolve(trouble);
        if (resolve){
            resolve=resolve&&handle(trouble);
        }

        if (resolve==expected){
            if (next!=null){
                next.apply(trouble);
            }else {
                System.out.println("处理完成");
            }
        }


    }



}