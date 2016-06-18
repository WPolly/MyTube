package com.tcl.lishanwang.mytube.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.fragment.LocalDownloadFragment;
import com.tcl.lishanwang.mytube.fragment.ServerDownloadFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DownloadInfoActivity extends AppCompatActivity {

    @InjectView(R.id.tl_download_info)
    TabLayout mTlDownloadInfo;
    @InjectView(R.id.vp_download_info)
    ViewPager mVpDownloadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_info);
        ButterKnife.inject(this);
        mVpDownloadInfo.setAdapter(new DownloadInfoAdapter(getSupportFragmentManager()));
        initToolbar();
        initTabLayout();
    }

    private void initTabLayout() {
        mTlDownloadInfo.setTabMode(TabLayout.MODE_FIXED);
        mTlDownloadInfo.setTabTextColors(Color.GRAY, Color.BLACK);
        mTlDownloadInfo.setupWithViewPager(mVpDownloadInfo);
    }

    private void initToolbar() {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.main_toolbar));
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("下载详情");
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

    class DownloadInfoAdapter extends FragmentPagerAdapter {

        public DownloadInfoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ServerDownloadFragment();

                case 1:
                    return new LocalDownloadFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position==0?"离线下载":"本地下载";
        }
    }
}
