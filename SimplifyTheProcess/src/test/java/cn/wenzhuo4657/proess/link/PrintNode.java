package cn.wenzhuo4657.proess.link;

import cn.wenzhuo4657.process.link.NoSupport;
import cn.wenzhuo4657.process.link.Support;
import cn.wenzhuo4657.process.link.Trouble;


public class PrintNode extends Support<MyTrouble> {

    public PrintNode() {
        super("打印节点");
    }

    @Override
    public boolean handle(MyTrouble trouble) {
        System.out.println("PrintNode处理问题: " + trouble.getNumber());
        return true;
    }


}


