package cn.wenzhuo4657.proess.link;

import cn.wenzhuo4657.process.link.LinkBuilder;
import cn.wenzhuo4657.process.link.Node;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testLinkBuilder() throws Exception {
        // 方式1：使用链式调用构建责任链
        Node<MyTrouble> chain1 = LinkBuilder.<MyTrouble>create()
                .add(new PrintNode())
                .add(new PrintANode())
                .build();
        
        System.out.println("=== 测试链式调用构建的责任链 ===");
        chain1.apply(new MyTrouble(1));
        
        System.out.println("\n=== 测试静态方法快速构建责任链 ===");
        // 方式2：使用静态方法快速构建
        Node<MyTrouble> chain2 = LinkBuilder.createChain(
                new PrintNode(),
                new PrintANode()
        );
        
        chain2.apply(new MyTrouble(2));
        
        System.out.println("\n=== 测试空链 ===");
        // 方式3：创建空链（只有根节点）
        Node<MyTrouble> emptyChain = LinkBuilder.createChain();
        emptyChain.apply(new MyTrouble(3));
    }
}
