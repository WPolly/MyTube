package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/6/3.
 * 9:58
 */
public class ConnectSocketRequestBean {



    /**
     * uid : u100000014
     * name : xiaohei
     * client : android
     */

    public ConnectSocketRequestBean(String uid, String name, String client) {
        this.uid = uid;
        this.name = name;
        this.client = client;
    }

    public String uid;
    public String name;
    public String client;
}
