package com.ebupt.bitwise;

import sun.applet.Main;

/**
 * @Author: yushibo
 * @Date: 2019/6/2 0002 21:31
 * @Description:
 */

public class Permission {

    public static final int ALLOW_SELECT = 1 << 0; //1
    public static final int ALLOW_INSERT = 1 << 1; //2
    public static final int ALLOW_UPDATE = 1 << 2; //4
    public static final int ALLOW_DELETE = 1 << 3; //8

    //存储目前的权限状态
    private int flag;
    //设置用户的权限
    public void setPer(int per){
        flag = per;
    }
    //增加用户的权限（1个或多个）
    public void enable(int per){
       flag=  flag | per;
    }
    //删除用户权限
    public void disable(int per){
        flag=  flag & ~per;
    }
    //判断用户的权限
    public boolean isAllow(int per){
        return (flag & per) == per;
    }
    //判断用户没有的权限
    public boolean isNotAllow(int per){
        return (flag & per) == 0;
    }




    public static void main(String[] args) {
        int flag = 15;
        Permission permission = new Permission();
        permission.setPer(15);
        permission.disable(ALLOW_DELETE|ALLOW_INSERT);
        System.out.println("select:"+permission.isAllow(ALLOW_SELECT));
        System.out.println("update:"+permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert:"+permission.isAllow(ALLOW_INSERT));
        System.out.println("delete:"+permission.isAllow(ALLOW_DELETE));
    }
}
