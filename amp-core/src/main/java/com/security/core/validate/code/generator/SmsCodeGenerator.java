package com.security.core.validate.code.generator;

import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ValidateCode;
import com.security.core.validate.code.generator.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;




public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties ;

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength()) ;
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
