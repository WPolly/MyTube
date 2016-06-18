package com.tcl.lishanwang.mytube.holder;

import android.view.View;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.utils.UIUtils;


/**
 * Created by xiaoshan on 2016/2/21.
 * 19:27
 */
public class LoadMoreHolder {

    public static final int STATE_NONE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_RETRY = 2;

    private View mLoadingView;
    private View mRetryView;

    public View initRootView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        mLoadingView = rootView.findViewById(R.id.item_loadmore_container_loading);
        mRetryView = rootView.findViewById(R.id.item_loadmore_container_retry);
        return rootView;
    }

    public void refreshUI(int state) {
        mLoadingView.setVisibility(View.GONE);
        mRetryView.setVisibility(View.GONE);

        switch (state) {
            case STATE_NONE:
                break;

            case STATE_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                break;

            case STATE_RETRY:
                mRetryView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
