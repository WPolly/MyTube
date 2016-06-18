package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/5/24.
 * 13:43
 */
public class LogoutResponseBean {

    /**
     * req_path : /user/logout
     * response_data : null
     * error : {"error_id":0,"error_desc":"success"}
     */

    public String req_path;
    public Object response_data;
    /**
     * error_id : 0
     * error_desc : success
     */

    public ErrorBean error;

    public static class ErrorBean {
        public int error_id;
        public String error_desc;
    }
}
