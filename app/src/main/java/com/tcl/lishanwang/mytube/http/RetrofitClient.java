package com.tcl.lishanwang.mytube.http;

import com.tcl.lishanwang.mytube.config.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiaoshan on 2016/5/23.
 * 15:40
 */
public class RetrofitClient {
    private static final long TIMEOUT_READ = 5000;
    private static final long TIMEOUT_CONNECTION = 10000;
    private static RetrofitClient ourInstance;

    private MyTubeService mTubeService;
    private SocketService mSocketService;

    public static RetrofitClient getInstance() {
        if (ourInstance == null) {
            synchronized (RetrofitClient.class) {
                if (ourInstance == null) {
                    ourInstance = new RetrofitClient();
                }
            }
        }

        return ourInstance;
    }

    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(Constants.URLS.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit socketBuilder = new Retrofit.Builder()
                .baseUrl(Constants.URLS.BASE_SOCKET_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTubeService = builder.create(MyTubeService.class);
        mSocketService = socketBuilder.create(SocketService.class);
    }

    public MyTubeService getTubeService() {
        return mTubeService;
    }

    public SocketService getSocketService() {
        return mSocketService;
    }
}
