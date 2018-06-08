package com.security.core.validate.code.processor;

import com.security.core.validate.code.ValidateCode;
import com.security.core.validate.code.ValidateCodeProcessor;
import com.security.core.validate.code.generator.ValidateCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<C> implements ValidateCodeProcessor {


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy() ;


    /**
     * 收集系统中所有 validateCodeGenerator 的实现 ,spring的依赖查找
     *
     * */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators ;

    @Override
    public void create(ServletWebRequest request) throws Exception {
      C validateCode =   generator(request) ;
      save(request,validateCode) ;
      send(request,validateCode) ;
    }

    private C generator(ServletWebRequest request){
        String  type = getProcessType(request) ;
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator") ;
        return (C)validateCodeGenerator.generator(request) ;
    }

    private  void  save(ServletWebRequest request,C validateCode){
        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX+getProcessType(request).toUpperCase(),validateCode);
    }

    protected abstract  void send(ServletWebRequest request,C validateCode) throws  Exception ;

    private String getProcessType(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");

    }
}
