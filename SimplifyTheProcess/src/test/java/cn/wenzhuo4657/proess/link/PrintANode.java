package cn.wenzhuo4657.proess.link;

import cn.wenzhuo4657.process.link.Support;

public class PrintANode extends Support<MyTrouble> {

    public PrintANode() {
        super("打印节点");
    }

    @Override
    public boolean handle(MyTrouble trouble) {
        System.out.println("PrintANode处理问题: " + trouble.getNumber());
        return true;
    }

   
}