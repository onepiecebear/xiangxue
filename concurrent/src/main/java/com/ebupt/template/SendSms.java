package com.ebupt.template;

/**
 * @Author: yushibo
 * @Date: 2019/5/30 17:40
 * @Description: 实现模板方法模式
 */
public class SendSms extends SendCustom{
    @Override
    public void to() {
        System.out.println("mark");
    }

    @Override
    public void from() {
        System.out.println("yu");
    }

    @Override
    public void content() {
        System.out.println("晚上吃什么");
    }

    @Override
    public void send() {
        System.out.println("调用API接口");
    }

    public static void main(String[] args) {
        SendCustom sendSms = new SendSms();
        sendSms.sendMessage();
    }
}
