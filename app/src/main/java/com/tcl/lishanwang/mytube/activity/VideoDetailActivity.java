package com.tcl.lishanwang.mytube.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.base.BaseLoadingPager;
import com.tcl.lishanwang.mytube.bean.BaseResponseBean;
import com.tcl.lishanwang.mytube.bean.ConnectSocketRequestBean;
import com.tcl.lishanwang.mytube.bean.ConnectSocketResponseBean;
import com.tcl.lishanwang.mytube.bean.OfflineDownloadRequestBean;
import com.tcl.lishanwang.mytube.bean.ServerDownloadInfoBean;
import com.tcl.lishanwang.mytube.bean.VideoDetailResponseBean;
import com.tcl.lishanwang.mytube.config.Constants;
import com.tcl.lishanwang.mytube.http.RetrofitClient;
import com.tcl.lishanwang.mytube.http.socket.WebSocketClient;
import com.tcl.lishanwang.mytube.utils.BitmapHelper;
import com.tcl.lishanwang.mytube.utils.LogUtils;
import com.tcl.lishanwang.mytube.utils.UIUtils;
import com.tcl.lishanwang.mytube.view.ProgressButton;

import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailActivity extends AppCompatActivity {


    private static final String TAG = "VideoDetailActivity";
    @InjectView(R.id.iv_video_detail_preview)
    ImageView mIvVideoDetailPreview;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.ns_video_detail_container)
    NestedScrollView mNsVideoDetailContainer;
    @InjectView(R.id.fab_play_video)
    FloatingActionButton mFabPlayVideo;
    private VideoDetailResponseBean mVideoDetailBean;
    private String mVideoId;
    private boolean isDesExpanded = true;
    private String mDownloadUrl;
    private String mPreviewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.inject(this);
        initToolbar();
        mFabPlayVideo.setClickable(false);
        mFabPlayVideo.setBackgroundColor(Color.GRAY);
        mFabPlayVideo.setUseCompatPadding(true);
        mVideoId = getIntent().getStringExtra("video_id");
        VideoDetailLoadingPager videoDetailLoadingPager = new VideoDetailLoadingPager(this);
        mNsVideoDetailContainer.addView(videoDetailLoadingPager);
        videoDetailLoadingPager.loadData();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mCollapsingToolbarLayout.setTitle("详情");
    }

    @OnClick(R.id.fab_play_video)
    public void onClick() {
        if (!TextUtils.isEmpty(mDownloadUrl)) {
            playVideo(mDownloadUrl);
            return;
        }

        if (!TextUtils.isEmpty(mPreviewUrl)) {
            playVideo(mPreviewUrl);
        }
    }

    private void playVideo(String videoUrl) {
        Intent intent = new Intent(this, VideoPlayActivity.class);
        intent.setData(Uri.parse(videoUrl));
        startActivity(intent);
    }

    class VideoDetailLoadingPager extends BaseLoadingPager {

        @InjectView(R.id.tv_video_detail_des)
        TextView mTvVideoDetailDes;
        @InjectView(R.id.detailViewCountView)
        TextView mDetailViewCountView;
        @InjectView(R.id.detailThumbsUpCountView)
        TextView mDetailThumbsUpCountView;
        @InjectView(R.id.detailThumbsDownCountView)
        TextView mDetailThumbsDownCountView;
        @InjectView(R.id.detailUploaderThumbnailView)
        CircleImageView mDetailUploaderThumbnailView;
        @InjectView(R.id.detailUploaderView)
        TextView mDetailUploaderView;
        @InjectView(R.id.tv_video_detail_title)
        TextView mTvVideoDetailTitle;
        @InjectView(R.id.pb_download)
        ProgressButton mPbDownload;

        public VideoDetailLoadingPager(Context context) {
            super(context);
        }

        @Override
        protected View initSuccessView() {
            View rootView = View.inflate(VideoDetailActivity.this, R.layout.content_video_detail, null);
            ButterKnife.inject(this, rootView);
            BitmapHelper.display(mIvVideoDetailPreview, mVideoDetailBean.response_data.preview_url);
            mTvVideoDetailTitle.setText(mVideoDetailBean.response_data.title);
            mTvVideoDetailDes.setText(Html.fromHtml(mVideoDetailBean.response_data.description));
            mDetailViewCountView.setText(UIUtils.getString(R.string.view_count_text, mVideoDetailBean.response_data.viewcount));
            mDetailThumbsUpCountView.setText(mVideoDetailBean.response_data.likecount + "");
            mDetailThumbsDownCountView.setText(mVideoDetailBean.response_data.dislikecount + "");
            BitmapHelper.display(mDetailUploaderThumbnailView, mVideoDetailBean.response_data.authorurl);
            mDetailUploaderView.setText(mVideoDetailBean.response_data.author);
            if (mVideoDetailBean.response_data.preview.status == 3) {
                mFabPlayVideo.setClickable(true);
                mPreviewUrl = mVideoDetailBean.response_data.preview.downloadurl;
            }
            if (mVideoDetailBean.response_data.formats.get(0).status == 3) {
                mPbDownload.setProgressButtonEnable(true);
                mPbDownload.setMaxProgress(1000);
                mPbDownload.setProgress(1000);
                mPbDownload.setText("离线下载完成, 点击在线播放");
                mDownloadUrl = mVideoDetailBean.response_data.formats.get(0).downloadurl;
                mFabPlayVideo.setClickable(true);
            }
//            System.out.println(mVideoDetailBean.response_data.formats.get(1).downloadurl+mVideoDetailBean.response_data.formats.get(0).status+mVideoDetailBean.response_data.formats.get(0).progress);
            return rootView;
        }

        @Override
        protected LoadedResult initData() {
            try {
                String accessToken = PreferenceManager.getDefaultSharedPreferences(VideoDetailActivity.this).getString(Constants.KEY.SP_ACCESS_TOKEN, "");
                mVideoDetailBean = RetrofitClient.getInstance().getTubeService().getVideoInfo(accessToken, mVideoId).execute().body();
                if (mVideoDetailBean != null) {
                    return LoadedResult.SUCCESS;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return LoadedResult.ERROR;
            }
            return LoadedResult.EMPTY;
        }


        @OnClick({R.id.tv_video_detail_title, R.id.pb_download})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_video_detail_title:
                    mTvVideoDetailDes.setVisibility(isDesExpanded ? GONE : VISIBLE);
                    isDesExpanded = !isDesExpanded;
                    break;
                case R.id.pb_download:
                    if (mVideoDetailBean.response_data.formats.get(0).status == 3) {
                        playVideo(mDownloadUrl);
                        return;
                    }

                    final List<BasicNameValuePair> extraHeaders = Arrays.asList(
//                            new BasicNameValuePair("key", "eOZ6a5R0gl0CZ32HbU7kzst9ASkfjlufK0kGfRZwwroG0WO_wcK1r2msrrH7LUj-VII5taeRQRIPFCYzQJ48oBMB9RqZBMa8v38ZhAzLlpp3suzlxQ-Wb4Qx3f3CdMgk2LW-DdDlVrJotS3K1RUG0aRQMDAOiz3YNZiO4i-c_LiIyw7jDDUObQxnJxWbvsfk")
//                            ,new BasicNameValuePair("token", "vdZCXDC145wKggLXCrsIo7MZsLDZlwNV54UBci-X2VSxfbgAl0Xbwt8JNRCIG7jPkBxXBFlQ5Y7rWAWjc34wkSyDXzolGSF26sCaVSXRklf4pCFDEu2Xke4UAnRKUeeiP6yyHF1Rf0snx3f96ldf_XsrJiwK_ZO-8AXTv3obGZ2RNZZbAeU7Fv01g_K3-RFlujaE2jAKGL66TdIKXOGX21GVCgg-8kib-VOSC8R457o")
                    );

                    if (TextUtils.isEmpty(UIUtils.getPushToken())) {
                        System.out.println("user_id: "+UIUtils.getUserId());
                        System.out.println("login_name: "+UIUtils.getLoginName());
                        ConnectSocketRequestBean connectSocketRequestBean = new ConnectSocketRequestBean(UIUtils.getUserId(), UIUtils.getLoginName(), "android");
                        RetrofitClient.getInstance().getSocketService().getPushToken(connectSocketRequestBean).enqueue(new Callback<ConnectSocketResponseBean>() {
                            @Override
                            public void onResponse(Call<ConnectSocketResponseBean> call, Response<ConnectSocketResponseBean> response) {

                                if (response.body() == null) {
                                    Toast.makeText(VideoDetailActivity.this, "response == null"+response.code(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                LogUtils.d(response.body().token);
                                PreferenceManager.getDefaultSharedPreferences(UIUtils.getContext())
                                        .edit()
                                        .putString(Constants.KEY.SP_PUSH_TOKEN, response.body().token)
                                        .apply();

                                connectToWebSocket(extraHeaders);
                            }

                            @Override
                            public void onFailure(Call<ConnectSocketResponseBean> call, Throwable t) {
                                Toast.makeText(VideoDetailActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                        return;
                    }

                    connectToWebSocket(extraHeaders);

                    break;
            }
        }

        private void connectToWebSocket(List<BasicNameValuePair> extraHeaders) {
            WebSocketClient client = new WebSocketClient(URI.create("ws://172.24.222.44:8080/v1.0/cms?key="+ Constants.AUTHORISATION_KEY+"&token="+UIUtils.getPushToken()), new WebSocketClient.Listener() {

                ServerDownloadInfoBean serverDownloadInfoBean;
                Gson gson = new Gson();

                @Override
                public void onConnect() {
                    Log.d(TAG, "Connected!");
                }

                @Override
                public void onMessage(String message) {
//                            Log.d(TAG, String.format("Got string message! %s", message));
                    serverDownloadInfoBean = gson.fromJson(message, ServerDownloadInfoBean.class);
                    UIUtils.postTaskSafely(new Runnable() {
                        @Override
                        public void run() {
                            mPbDownload.setText("正在离线下载..");
                            mPbDownload.setProgressButtonEnable(true);
                            mPbDownload.setMaxProgress(1000);
                            mPbDownload.setProgress(serverDownloadInfoBean.preview.progress);
                            System.out.println(serverDownloadInfoBean.downloadstatus.progress);
                            mPbDownload.setClickable(false);
                            if (serverDownloadInfoBean.downloadstatus.progress == 1000 && !TextUtils.isEmpty(serverDownloadInfoBean.downloadstatus.download_url)) {
                                mPbDownload.setText("离线下载完成, 点击在线播放");
                                mDownloadUrl = serverDownloadInfoBean.downloadstatus.download_url;
                                mPbDownload.setClickable(true);
                                mFabPlayVideo.setClickable(true);
                            }
                        }
                    });
                }

                @Override
                public void onMessage(byte[] data) {
//                            Log.d(TAG, String.format("Got binary message! %s", new String(data)));
                }

                @Override
                public void onDisconnect(int code, String reason) {
                    Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
                }

                @Override
                public void onError(Exception error) {
                    Log.e(TAG, "Error!", error);
                }
            }, extraHeaders);

            client.connect();

            RetrofitClient.getInstance()
                    .getTubeService()
                    .startOfflineDownload(new OfflineDownloadRequestBean(UIUtils.getAccessToken(), UIUtils.getPushToken(), mVideoId, mVideoDetailBean.response_data.formats.get(0).itag, true, UIUtils.getDeviceId()))
                    .enqueue(new Callback<BaseResponseBean>() {
                        @Override
                        public void onResponse(Call<BaseResponseBean> call, Response<BaseResponseBean> response) {
                            if (response.body().error.error_id == 0) {
                                mPbDownload.setText("离线下载开始..");
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponseBean> call, Throwable t) {

                        }
                    });
        }
    }
}
