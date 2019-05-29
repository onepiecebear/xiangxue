package com.ebupt.utils;

import sun.applet.Main;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @Author: yushibo
 * @Date: 2019/5/29 13:41
 * @Description: Exchange使用
 */
public class UseExchange {

    private static final Exchanger<Set<String>> exchange = new Exchanger<>();

    public static void main(String[] args) {

        //第一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();
                try {
                    setA = exchange.exchange(setA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        //第二个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();
                try {
                    setB = exchange.exchange(setB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
