package com.ebupt.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 0030 0:10
 * @Description: 引用类型的原子操作类
 */

public class UseAtomicReference {

    static AtomicReference<UserInfo> userReference = new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo user = new UserInfo("Mark",15);
        userReference.set(user);
        UserInfo userChange = new UserInfo("Bill",17);
       //(期望值，更改值)
        userReference.compareAndSet(user,userChange);

        System.out.println(userReference.get().getName());
        System.out.println(userReference.get().getAge());
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }

    static class UserInfo{
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
