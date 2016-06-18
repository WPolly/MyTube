package com.tcl.lishanwang.mytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.bean.RecommendResponseBean;
import com.tcl.lishanwang.mytube.fragment.RecommendFragment;
import com.tcl.lishanwang.mytube.fragment.SearchFragment;
import com.tcl.lishanwang.mytube.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.iv_download)
    ImageView mIvDownload;
    @InjectView(R.id.iv_my_info)
    ImageView mIvMyInfo;
    @InjectView(R.id.et_search)
    EditText mEtSearch;
    @InjectView(R.id.iv_search)
    ImageView mIvSearch;
    @InjectView(R.id.fl_main_content_container)
    FrameLayout mFlMainContentContainer;
    @InjectView(R.id.tv_register)
    TextView mTvRegister;
    @InjectView(R.id.tv_login)
    TextView mTvLogin;
    private FragmentManager mSupportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        mSupportFragmentManager = getSupportFragmentManager();
        RecommendFragment fragment = new RecommendFragment();
        mSupportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content_container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = sp.getBoolean("is_login", false);
        mTvLogin.setVisibility(isLogin?View.GONE:View.VISIBLE);
        mTvRegister.setVisibility(isLogin?View.GONE:View.VISIBLE);
        mIvDownload.setVisibility(isLogin?View.VISIBLE:View.GONE);
        mIvMyInfo.setVisibility(isLogin?View.VISIBLE:View.GONE);
    }

    @OnClick({R.id.iv_download, R.id.iv_my_info, R.id.iv_search, R.id.tv_register, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_download:
                gotoOtherActivity(DownloadInfoActivity.class);
                break;
            case R.id.iv_my_info:
                gotoOtherActivity(MyInfoActivity.class);
                break;
            case R.id.iv_search:
                String keyword = mEtSearch.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    mEtSearch.setError("搜索关键词不能为空哦");
                } else {
                    doSearchAndShow(keyword);
                }
                break;
            case R.id.tv_register:
                gotoOtherActivity(RegisterActivity.class);
                break;
            case R.id.tv_login:
                gotoOtherActivity(LoginActivity.class);
                break;
        }
    }

    public void doSearchAndShow(String keyword) {
        SearchFragment fragment = new SearchFragment();
        fragment.setKeyword(keyword);
        mSupportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void gotoOtherActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
