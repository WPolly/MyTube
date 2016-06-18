package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/5/23.
 * 15:23
 */
public class RegisterRequestBean {

    /**
     * username : user10086
     * password : 123456
     * email : user@example.com
     * deviceid : asfdsfdsrewrewewrsfdsfdsfer
     */

    public String username;
    public String password;
    public String email;
    public String deviceid;

    public RegisterRequestBean(String username, String password, String email, String deviceid) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.deviceid = deviceid;
    }
}
