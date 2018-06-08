package com.security.core.validate.code;

import com.security.core.exception.ValidateCodeException;
import com.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler ;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy() ;

    private Set<String> urls = new HashSet<>() ;

    private SecurityProperties securityProperties  ;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String urlString = securityProperties.getCode().getImage().getUrl();
        if (StringUtils.isNotEmpty(urlString)) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/form") ;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false ;
        for(String url : urls){
            if(pathMatcher.match(url,request.getRequestURI())){
                action = true ;
            }
        }
        if(action){
                    try {
                        validate(new ServletWebRequest(request)) ;
                    }catch (ValidateCodeException e){
                        authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                        return ;
                    }


            }
            filterChain.doFilter(request,response);

    }



    private void validate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,"SESSION_KEY_IMAGE_CODE");
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode") ;
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码值不能为空");
        }
        if (Objects.isNull(codeInSession)) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpired()) {
            throw new ValidateCodeException("验证已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证已不匹配");
        }
        sessionStrategy.removeAttribute(request,"SESSION_KEY_IMAGE_CODE");

    }


    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
