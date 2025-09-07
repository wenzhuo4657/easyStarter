package cn.wenzhuo4657.process.link;

/**
 * 责任链构建器
 * 支持静态方法调用和链式构建
 */
public class LinkBuilder<T extends Trouble> {

    private Node<T> root;
    private Node<T> current;

    private LinkBuilder() {
        this.root = new NoSupport<T>("root");
        this.current = root;
    }

    /**
     * 创建责任链构建器
     */
    public static <T extends Trouble> LinkBuilder<T> create() {
        return new LinkBuilder<>();
    }

    /**
     * 添加节点到责任链
     */
    public LinkBuilder<T> add(Node<T> node) {
        current.setNext(node);
        current = node;
        return this;
    }

    /**
     * 构建责任链，返回根节点
     */
    public Node<T> build() {
        return root;
    }

    /**
     * 静态方法：快速创建简单的责任链
     */
    @SafeVarargs
    public static <T extends Trouble> Node<T> createChain(Node<T>... nodes) {
        if (nodes == null || nodes.length == 0) {
            return new NoSupport<T>("root");
        }
        
        LinkBuilder<T> builder = create();
        for (Node<T> node : nodes) {
            builder.add(node);
        }
        return builder.build();
    }
}
