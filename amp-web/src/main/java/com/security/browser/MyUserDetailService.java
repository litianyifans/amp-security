package com.security.browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger log = LoggerFactory.getLogger(MyUserDetailService.class) ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            log.info("登陆的用户名是："+ userName);
            log.info(passwordEncoder.encode("123456"));
        return new User(userName,passwordEncoder.encode("123456"),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
