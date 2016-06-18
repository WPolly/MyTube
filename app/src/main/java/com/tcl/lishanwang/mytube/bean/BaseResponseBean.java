package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/6/3.
 * 15:12
 */
public class BaseResponseBean {

    /**
     * req_path : /video/offlinedownload
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
