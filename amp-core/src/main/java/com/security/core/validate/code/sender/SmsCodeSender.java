package com.security.core.validate.code.sender;

public interface SmsCodeSender {

    void  send(String mobile , String message) ;
}
