package com.security.rest;

import com.security.dto.User;
import com.security.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class HelloApi {



    @RequestMapping(value="/sayHello")
    public String sayHello(){
        throw  new UserNotFoundException("123");
    }

    @RequestMapping(value="/getUserName/{id}")
    public String getUserName(@PathVariable(value = "id") int id){
        System.out.println("hallo world");
        return "hello world"+id ;
    }

    @RequestMapping(value="/sayBay/{id}/{symbolicName:[a-z-]+}")
    public String sayBay(@PathVariable(value = "id") int userId,@PathVariable String symbolicName){
        System.out.println("hallo world");
        return "hello world"+userId +symbolicName;
    }

    @GetMapping("/me")
    @JsonView(User.UserSimpleView.class)
    public User getMe(){
        User user = new User() ;
        user.setId("24");
        user.setUsername("liwang");
        user.setBirthday(new Date());
        user.setPassword("sdafas");
        return user ;

    }

    @PostMapping("/forme")
    @JsonView(User.UserSimpleView.class)
    public User forme(@Valid @RequestBody User user ){
     /*   if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }*/
        return user ;
    }

    @GetMapping("/getAuthentication")
    public Object getauthentication(Authentication authentication){
        return authentication ;
        //return SecurityContextHolder.getContext().getAuthentication() ;
    }

    @GetMapping("/getUser")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user){
        return user ;
        //return SecurityContextHolder.getContext().getAuthentication() ;
    }
}
