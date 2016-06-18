package com.tcl.lishanwang.mytube.config;

import com.tcl.lishanwang.mytube.utils.LogUtils;

/**
 * Created by xiaoshan on 2016/5/10.
 * 10:36
 */
public class Constants {
    public static final int DEBUG_LEVEL = LogUtils.LEVEL_ALL;//LEVEL_ALL,显示所有的日子,OFF:关闭日志的显示
    public static final long VALIDATE_TIME = 50 * 60 * 1000;
    public static final int PAGE_SIZE = 20;
    public static final String AUTHORISATION_KEY = "eOZ6a5R0gl0CZ32HbU7kzst9ASkfjlufK0kGfRZwwroG0WO_wcK1r2msrrH7LUj-VII5taeRQRIPFCYzQJ48oBMB9RqZBMa8v38ZhAzLlpp3suzlxQ-Wb4Qx3f3CdMgk2LW-DdDlVrJotS3K1RUG0aRQMDAOiz3YNZiO4i-c_LiIyw7jDDUObQxnJxWbvsfk";

    public static final class URLS {
        public static final String BASE_URL = "http://172.24.222.44:3000/";
        public static final String BASE_SOCKET_URL = "http://172.24.222.44:8080/";
    }

    public static final class TIME {
        public static final long SECOND_MILLIS = 1000;
        public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
        public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    }

    public static final class KEY {
        public static final String SP_IS_LOGIN = "is_login";
        public static final String SP_ACCESS_TOKEN = "access_token";
        public static final String SP_USER_ID = "user_id";
        public static final String SP_USERNAME = "username";
        public static final String SP_PASSWORD = "password";
        public static final String SP_PUSH_TOKEN = "push_token";
    }
}
