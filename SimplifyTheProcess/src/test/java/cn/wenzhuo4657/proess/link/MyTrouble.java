package cn.wenzhuo4657.proess.link;

import cn.wenzhuo4657.process.link.Trouble;

public class MyTrouble implements Trouble {
    private int number;
    public MyTrouble(int number)
    {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
