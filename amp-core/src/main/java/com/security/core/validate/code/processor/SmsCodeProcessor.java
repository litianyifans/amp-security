package com.security.core.validate.code.processor;


import com.security.core.validate.code.ValidateCode;
import com.security.core.validate.code.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;



@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {


    @Autowired
    private SmsCodeSender smsCodeSender ;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
       String mobile =  ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
       smsCodeSender.send(mobile,validateCode.getCode());
    }
}
