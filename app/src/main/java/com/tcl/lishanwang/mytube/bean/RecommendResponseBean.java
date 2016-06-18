package com.tcl.lishanwang.mytube.bean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/6/8.
 * 11:13
 */
public class RecommendResponseBean {

    /**
     * req_path : /search/getrecommendlist
     * response_data : {"traffic":["手环","NBA","电影","好莱坞","中超","VR","美剧","京剧","游戏","互联网"]}
     * error : {"error_id":0,"error_desc":"success"}
     */

    public String req_path;
    public ResponseDataBean response_data;
    /**
     * error_id : 0
     * error_desc : success
     */

    public ErrorBean error;

    public static class ResponseDataBean {
        public List<String> traffic;
    }

    public static class ErrorBean {
        public int error_id;
        public String error_desc;
    }
}
