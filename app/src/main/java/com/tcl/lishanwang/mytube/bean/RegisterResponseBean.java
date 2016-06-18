package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/5/23.
 * 15:26
 */
public class RegisterResponseBean {

    /**
     * req_path : /user/login
     * response_data : {"access_token":"hDd5mqd657OjXt7Y2j0MmeyzH98wqEJvQ7Jr-Pcpy3iuOxlD4chFJhgP7m5JfNQRhJpYZHTSp60b9y1aFY_6ZW-kEFq0_LVdp2yB7yAL7vS7kIAc0TTZnnfnXLgezgd5H8Yoa3_ofcJM4jVQCC6owED5XW0SwBIbIQmul1ujUIM","expire":"1464850777","user_id":"u100000002"}
     * error : {"error_id":0,"error_desc":"success"}
     */

    public String req_path;
    /**
     * access_token : hDd5mqd657OjXt7Y2j0MmeyzH98wqEJvQ7Jr-Pcpy3iuOxlD4chFJhgP7m5JfNQRhJpYZHTSp60b9y1aFY_6ZW-kEFq0_LVdp2yB7yAL7vS7kIAc0TTZnnfnXLgezgd5H8Yoa3_ofcJM4jVQCC6owED5XW0SwBIbIQmul1ujUIM
     * expire : 1464850777
     * user_id : u100000002
     */

    public ResponseDataBean response_data;
    /**
     * error_id : 0
     * error_desc : success
     */

    public ErrorBean error;

    public static class ResponseDataBean {
        public String access_token;
        public String expire;
        public String user_id;
    }

    public static class ErrorBean {
        public int error_id;
        public String error_desc;
    }
}
