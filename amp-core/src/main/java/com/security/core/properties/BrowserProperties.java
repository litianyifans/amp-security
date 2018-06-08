package com.security.core.properties;

public class BrowserProperties {

    private String loginPage = "/demo-signin.html";

    private int tokenExpire = 604800;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public int getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(int tokenExpire) {
        this.tokenExpire = tokenExpire;
    }
}
