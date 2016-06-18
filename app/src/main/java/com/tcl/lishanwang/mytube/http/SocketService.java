package com.tcl.lishanwang.mytube.http;

import com.tcl.lishanwang.mytube.bean.ConnectSocketRequestBean;
import com.tcl.lishanwang.mytube.bean.ConnectSocketResponseBean;
import com.tcl.lishanwang.mytube.config.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by xiaoshan on 2016/6/8.
 * 17:42
 */
public interface SocketService {
    @POST("v1.0/token")
    @Headers("Authorization: key="+ Constants.AUTHORISATION_KEY)
    Call<ConnectSocketResponseBean> getPushToken(@Body ConnectSocketRequestBean requestBean);
}
