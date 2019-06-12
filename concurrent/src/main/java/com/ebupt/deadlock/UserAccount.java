package com.ebupt.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: yushibo
 * @Date: 2019/6/12 16:41
 * @Description:
 */
public class UserAccount {
    private String name;
    private int money;

//private final Lock lock = new ReentrantLock();

    public UserAccount(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int amount){

        money=money+amount;
    }
    public void flyMoney(int amount){
        money=money-amount;
    }


}
