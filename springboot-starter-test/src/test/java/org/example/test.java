package org.example;

import org.example.whiteList.DoJoinPoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Autowired
    private DoJoinPoint a;

    @Test
    public  void test(){
        System.out.println(a);
    }
}
