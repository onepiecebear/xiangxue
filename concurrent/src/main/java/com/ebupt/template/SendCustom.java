package com.ebupt.template;

import java.util.Date;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 17:36
 * @Description: 模板方法模式
 */
public abstract class SendCustom {

    public abstract void to();
    public abstract void from();
    public abstract void content();
    public void date(){
        System.out.println(new Date());
    }
    public abstract void send();

    //框架方法--模板方法
    public void sendMessage(){
        to();
        from();
        content();
        date();
        send();
   }

}
