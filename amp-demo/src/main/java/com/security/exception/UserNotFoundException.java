package com.security.exception;

public class UserNotFoundException extends RuntimeException {
    private String id ;

    public UserNotFoundException( String id) {
        super("用户不存在");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
