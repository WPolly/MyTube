package com.tcl.lishanwang.mytube.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.activity.SearchActivity;
import com.tcl.lishanwang.mytube.base.BaseFragment;
import com.tcl.lishanwang.mytube.base.BaseLoadingPager;
import com.tcl.lishanwang.mytube.bean.RecommendResponseBean;
import com.tcl.lishanwang.mytube.http.RetrofitClient;
import com.tcl.lishanwang.mytube.utils.UIUtils;
import com.tcl.lishanwang.mytube.view.randomfly.StellarMap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import retrofit2.Response;

/**
 * Created by xiaoshan on 2016/5/23.
 * 17:43
 */
public class RecommendFragment extends BaseFragment {

    private List<String> mData;
    private static final int PAGER_SIZE = 10;

    @Override
    public View initSuccessView() {
        StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        RecommendAdapter adapter = new RecommendAdapter();
        stellarMap.setAdapter(adapter);
        stellarMap.setGroup(0, true);
        stellarMap.setRegularity(15, 15);
        stellarMap.setBackgroundResource(R.drawable.recommend_bg);
        return stellarMap;
    }

    @Override
    public BaseLoadingPager.LoadedResult initData() {
        try {
            Response<RecommendResponseBean> recommendResponseBeanResponse = RetrofitClient.getInstance().getTubeService().getRecommendWords().execute();
            mData = recommendResponseBeanResponse.body().response_data.traffic;
            if (mData != null && mData.size() != 0) {
                return BaseLoadingPager.LoadedResult.SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return BaseLoadingPager.LoadedResult.ERROR;
        }
        return BaseLoadingPager.LoadedResult.EMPTY;
    }

    class RecommendAdapter implements StellarMap.Adapter, View.OnClickListener {

        @Override
        public int getGroupCount() {
            int count = mData.size() / PAGER_SIZE;
            if (mData.size() % PAGER_SIZE > 0) {
                count++;
            }
            return count;
        }

        @Override
        public int getCount(int group) {
            if (group == getGroupCount() - 1) {
                if (mData.size() % PAGER_SIZE > 0) {
                    return mData.size() % PAGER_SIZE;
                }
            }
            return PAGER_SIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {

            int index = group * PAGER_SIZE + position;
            final String text = mData.get(index);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(text);

            Random random = new Random();
            int textSize = random.nextInt(6) + 12;
            tv.setTextSize(textSize);

            int alpha = 255;
            int red = random.nextInt(180) + 30;
            int green = random.nextInt(180) + 30;
            int blue = random.nextInt(180) + 30;
            int argb = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);

            int padding = UIUtils.dip2Px(5);
            tv.setPadding(padding, padding, padding, padding);

            tv.setOnClickListener(this);

            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return (group+1)%getGroupCount();
        }

        @Override
        public void onClick(View v) {
            String s = ((TextView) v).getText().toString();
            ((SearchActivity) getActivity()).doSearchAndShow(s);
        }

    }
}
