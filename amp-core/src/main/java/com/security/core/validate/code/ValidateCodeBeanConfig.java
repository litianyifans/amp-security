package com.security.core.validate.code;

import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.generator.ImageCodeGenerator;
import com.security.core.validate.code.generator.SmsCodeGenerator;
import com.security.core.validate.code.generator.ValidateCodeGenerator;
import com.security.core.validate.code.sender.DefaultSmsCodeSender;
import com.security.core.validate.code.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties ;

    @Bean
    @ConditionalOnMissingBean(name = "iamgeCodeGenerator")
    public ImageCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator() ;
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return  imageCodeGenerator ;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public SmsCodeGenerator smsCodeGenerator() {
        SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
        smsCodeGenerator.setSecurityProperties(securityProperties);
        return smsCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender newSmsCodeSender() {
        return new DefaultSmsCodeSender();
    }



}
