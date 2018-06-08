package com.security.config;


import com.security.filter.ThirdFilter;
import com.security.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class WebMvcConfigAdaptor  extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;


    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors();
        configurer.registerDeferredResultInterceptors();
        // configurer.setTaskExecutor(null);
        configurer.setDefaultTimeout(5000);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor).addPathPatterns("/user/*").excludePathPatterns("/user/5");
    }


    @Bean
    public FilterRegistrationBean thirdFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
       filterRegistrationBean.setFilter(new ThirdFilter());
        List<String> urls = new ArrayList<String>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
