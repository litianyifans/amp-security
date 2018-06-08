package com.security.core.validate.code.generator;


import com.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    public ValidateCode generator(ServletWebRequest request) ;
}
