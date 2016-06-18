package com.tcl.lishanwang.mytube.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.bean.LeftTrafficResponseBean;
import com.tcl.lishanwang.mytube.bean.LogoutRequestBean;
import com.tcl.lishanwang.mytube.bean.LogoutResponseBean;
import com.tcl.lishanwang.mytube.http.RetrofitClient;
import com.tcl.lishanwang.mytube.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInfoActivity extends AppCompatActivity {

    @InjectView(R.id.tv_login_name)
    TextView mTvLoginName;
    @InjectView(R.id.tv_login_account)
    TextView mTvLoginAccount;
    @InjectView(R.id.cv_traffic_unused)
    CardView mCvTrafficUnused;
    @InjectView(R.id.cv_about_us)
    CardView mCvAboutUs;
    @InjectView(R.id.cv_clear_cache)
    CardView mCvClearCache;
    @InjectView(R.id.cv_logout)
    CardView mCvLogout;
    @InjectView(R.id.tv_left_traffic)
    TextView mTvLeftTraffic;
    @InjectView(R.id.iv_loading)
    ImageView mIvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.inject(this);
        initToolbar();
        mTvLoginName.setText(UIUtils.getLoginName());
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.main_toolbar));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("我");
        }
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @OnClick({R.id.cv_traffic_unused, R.id.cv_about_us, R.id.cv_clear_cache, R.id.cv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_traffic_unused:
                mIvLoading.setVisibility(View.VISIBLE);
                RetrofitClient.getInstance().getTubeService().getLeftTraffic(UIUtils.getAccessToken(), UIUtils.getDeviceId()).enqueue(new Callback<LeftTrafficResponseBean>() {
                    @Override
                    public void onResponse(Call<LeftTrafficResponseBean> call, Response<LeftTrafficResponseBean> response) {
                        if (response.body().response_data != null) {
                            mTvLeftTraffic.setText(response.body().response_data.traffic+"M");
                            mIvLoading.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(MyInfoActivity.this, "获取剩余流量失败,请稍后再试", Toast.LENGTH_SHORT).show();
                            mIvLoading.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<LeftTrafficResponseBean> call, Throwable t) {
                        Toast.makeText(MyInfoActivity.this, "获取剩余流量失败,请稍后再试", Toast.LENGTH_SHORT).show();
                        mIvLoading.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.cv_about_us:
                break;
            case R.id.cv_clear_cache:
                break;
            case R.id.cv_logout:
                String accessToken = UIUtils.getAccessToken();
                LogoutRequestBean logoutRequestBean = new LogoutRequestBean(accessToken);
                RetrofitClient.getInstance().getTubeService().logout(logoutRequestBean).enqueue(new Callback<LogoutResponseBean>() {
                    @Override
                    public void onResponse(Call<LogoutResponseBean> call, Response<LogoutResponseBean> response) {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyInfoActivity.this);
                            sp.edit().putBoolean("is_login", false).apply();
                            Toast.makeText(MyInfoActivity.this, "退出当前账号成功", Toast.LENGTH_SHORT).show();
                            mCvLogout.setClickable(false);
                            mTvLoginName.setText("未登录");
                    }

                    @Override
                    public void onFailure(Call<LogoutResponseBean> call, Throwable t) {
                        Toast.makeText(MyInfoActivity.this, "退出登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
