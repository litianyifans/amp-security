package com.security.validate.code;


import com.security.core.validate.code.ImageCode;
import com.security.core.validate.code.generator.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;



//@Component("iamgeCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    private Logger logger = LoggerFactory.getLogger(getClass());



    @Override
    public ImageCode generator(ServletWebRequest request) {
        logger.info("DemoImageCodeGenerator.generate");
        return null;
    }
}
