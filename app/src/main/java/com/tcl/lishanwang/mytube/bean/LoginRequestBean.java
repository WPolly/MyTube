package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/5/23.
 * 15:23
 */
public class LoginRequestBean {

    /**
     * username : user10086
     * password : 123456
     */

    public String username;
    public String password;

    public LoginRequestBean(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
