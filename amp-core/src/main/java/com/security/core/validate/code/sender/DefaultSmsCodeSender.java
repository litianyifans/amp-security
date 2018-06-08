package com.security.core.validate.code.sender;

public class DefaultSmsCodeSender implements SmsCodeSender {


    @Override
    public void send(String mobile, String message) {
        System.out.println("向手机"+mobile+"发送了一个短信验证码"+message);
    }
}
