package com.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */

    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE" ;

    /**
     * 创建验证码
     */

    void create(ServletWebRequest request) throws  Exception ;
}
