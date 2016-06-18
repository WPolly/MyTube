package com.tcl.lishanwang.mytube.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tcl.lishanwang.mytube.utils.UIUtils;

/**
 * Created by xiaoshan on 2016/5/24.
 * 9:35
 */
public abstract class BaseFragment extends Fragment {
    private BaseLoadingPager mLoadingPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mLoadingPager == null) {
            mLoadingPager = new BaseLoadingPager(getContext()) {
                @Override
                protected View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }

                @Override
                protected LoadedResult initData() {
                    return BaseFragment.this.initData();
                }
            };
        }
        return mLoadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoadingPager.loadData();
    }

    public BaseLoadingPager getLoadingPager() {
        if (mLoadingPager == null) {
            Toast.makeText(UIUtils.getContext(), "mLoadingPager == null", Toast.LENGTH_SHORT).show();
        }
        return mLoadingPager;
    }

    public abstract View initSuccessView();

    public abstract BaseLoadingPager.LoadedResult initData();
}
