package com.tcl.lishanwang.mytube.fragment;

import android.view.View;
import android.widget.ListView;

import com.tcl.lishanwang.mytube.adapter.VideoListAdapter;
import com.tcl.lishanwang.mytube.base.BaseFragment;
import com.tcl.lishanwang.mytube.base.BaseLoadingPager;
import com.tcl.lishanwang.mytube.bean.SearchResponseBean;
import com.tcl.lishanwang.mytube.factory.ListViewFactory;
import com.tcl.lishanwang.mytube.http.RetrofitClient;

import java.io.IOException;

/**
 * Created by xiaoshan on 2016/5/24.
 * 10:14
 */
public class SearchFragment extends BaseFragment {

    private SearchResponseBean mSearchResponseBean;
    private VideoListAdapter mVideoListAdapter;

    private String mKeyword;

    @Override
    public View initSuccessView() {
        ListView lvVideos = ListViewFactory.getListViewInstance();
        mVideoListAdapter = new VideoListAdapter(mSearchResponseBean.response_data.searchlist, getKeyword());
        lvVideos.setAdapter(mVideoListAdapter);
        return lvVideos;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        try {
            mSearchResponseBean = RetrofitClient.getInstance().getTubeService().search(getKeyword(), 1).execute().body();
            if (mSearchResponseBean == null || mSearchResponseBean.response_data.searchlist.size()==0) {
                return BaseLoadingPager.LoadedResult.EMPTY;
            } else {
                return BaseLoadingPager.LoadedResult.SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }
}
