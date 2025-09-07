package cn.wenzhuo4657.proess.tree;

import org.junit.Test;


public class ApiTest {


    /**
     * 规则树测试
     */
    @Test
    public void test() throws Exception
    {
        myRoot myRoot = new myRoot();
        myRoot.apply("1", "2");
    }
}
