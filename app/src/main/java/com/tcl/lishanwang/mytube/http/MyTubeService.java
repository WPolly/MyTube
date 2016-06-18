package com.tcl.lishanwang.mytube.http;

import com.tcl.lishanwang.mytube.bean.BaseResponseBean;
import com.tcl.lishanwang.mytube.bean.LeftTrafficResponseBean;
import com.tcl.lishanwang.mytube.bean.LoginRequestBean;
import com.tcl.lishanwang.mytube.bean.LogoutRequestBean;
import com.tcl.lishanwang.mytube.bean.LogoutResponseBean;
import com.tcl.lishanwang.mytube.bean.OfflineDownloadRequestBean;
import com.tcl.lishanwang.mytube.bean.RecommendResponseBean;
import com.tcl.lishanwang.mytube.bean.RegisterRequestBean;
import com.tcl.lishanwang.mytube.bean.RegisterResponseBean;
import com.tcl.lishanwang.mytube.bean.SearchResponseBean;
import com.tcl.lishanwang.mytube.bean.VideoDetailResponseBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * Created by xiaoshan on 2016/5/23.
 * 15:11
 */
public interface MyTubeService {
    @POST("/user/register")
    Call<RegisterResponseBean> register(@Body RegisterRequestBean registerRequestBean);

    @POST("/user/login")
    Call<RegisterResponseBean> login(@Body LoginRequestBean loginRequestBean);

    @POST("/user/logout")
    Call<LogoutResponseBean> logout(@Body LogoutRequestBean logoutRequestBean);

    @GET("/search/getrecommendlist")
    Call<RecommendResponseBean> getRecommendWords();

    @GET("/search/getsearchlist")
    Call<SearchResponseBean> search(@Query("keyword") String keyword, @Query("page") int page);

    @GET("/video/getinfo")
    Call<VideoDetailResponseBean> getVideoInfo(@Query("access_token") String accessToken, @Query("videoid") String videoId);

    @GET("/user/getlefttraffic")
    Call<LeftTrafficResponseBean> getLeftTraffic(@Query("access_token") String accessToken, @Query("deviceid") String deviceId);

    @GET("/{format}/{filename}")
    @Streaming
    Call<ResponseBody> downloadFile(@Path("format") String format, @Path("filename") String filename);

    @POST("/video/offlinedownload")
    Call<BaseResponseBean> startOfflineDownload(@Body OfflineDownloadRequestBean offlineDownloadRequestBean);
}
