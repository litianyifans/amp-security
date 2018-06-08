package com.security.browser;

import com.security.Filter.SecurityAuthenticationFailureHandler;
import com.security.Filter.SecurityAuthenticationSuccessHandler;
import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties ;

    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler ;

    @Autowired
    SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler ;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder() ;
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter() ;
        validateCodeFilter.setAuthenticationFailureHandler(securityAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();



        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/authentication/require")
            .loginProcessingUrl("/authentication/form")
            .failureHandler(securityAuthenticationFailureHandler)
            .successHandler(securityAuthenticationSuccessHandler)
            .and()
            .rememberMe()
            .tokenRepository(tokenRepository())
            .tokenValiditySeconds(securityProperties.getBrowser().getTokenExpire())
            .userDetailsService(userDetailsService)
            .and()
            .authorizeRequests()
            .antMatchers("/authentication/require",
            "/code/*",
            securityProperties.getBrowser().getLoginPage()
            ).permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();
    }
}
