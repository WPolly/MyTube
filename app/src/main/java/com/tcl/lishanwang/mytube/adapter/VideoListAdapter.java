package com.tcl.lishanwang.mytube.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.lishanwang.mytube.R;
import com.tcl.lishanwang.mytube.activity.VideoDetailActivity;
import com.tcl.lishanwang.mytube.bean.SearchResponseBean;
import com.tcl.lishanwang.mytube.config.Constants;
import com.tcl.lishanwang.mytube.factory.ThreadPoolFactory;
import com.tcl.lishanwang.mytube.holder.LoadMoreHolder;
import com.tcl.lishanwang.mytube.http.RetrofitClient;
import com.tcl.lishanwang.mytube.utils.BitmapHelper;
import com.tcl.lishanwang.mytube.utils.StringUtils;
import com.tcl.lishanwang.mytube.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiaoshan on 2016/5/11.
 * 18:23
 */
public class VideoListAdapter extends BaseAdapter {

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_LOAD_MORE = 1;
    public int mCurrentPage = 1;
    public String mKeyword;
    private LoadMoreHolder mLoadMoreHolder;
    private boolean isLoadingMore;

    public VideoListAdapter(List<SearchResponseBean.ResponseDataBean.SearchlistBean> searchlistBeen, String keyword) {
        mSearchlistBeen = searchlistBeen;
        mKeyword = keyword;
    }

    private List<SearchResponseBean.ResponseDataBean.SearchlistBean> mSearchlistBeen;

    @Override
    public int getCount() {
        return mSearchlistBeen.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mSearchlistBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
            mLoadMoreHolder = new LoadMoreHolder();
            View rootView = mLoadMoreHolder.initRootView();
            mLoadMoreHolder.refreshUI(LoadMoreHolder.STATE_LOADING);
            if (!isLoadingMore) {
                performLoadMore();
                isLoadingMore = true;
            }
            return rootView;
        } else {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_video_list, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mItemDurationView.setText(StringUtils.formatMillis(mSearchlistBeen.get(position).duration*1000));
            viewHolder.mItemVideoTitleView.setText(mSearchlistBeen.get(position).title);
            viewHolder.mItemViewCountView.setText(StringUtils.shortViewCount((long) mSearchlistBeen.get(position).viewcount));
            viewHolder.mItemUploaderView.setText(mSearchlistBeen.get(position).author);
            viewHolder.mItemUploadDateView.setText(mSearchlistBeen.get(position).datepublished+" • ");
            BitmapHelper.display(viewHolder.mItemThumbnailView, mSearchlistBeen.get(position).preview_url);
        viewHolder.mCVVideoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIUtils.getContext(), VideoDetailActivity.class);
                intent.putExtra("video_id", mSearchlistBeen.get(position).videoid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);
            }
        });
            return convertView;
        }

    }

    private void performLoadMore() {
        LoadMoreTask loadMoreTask = new LoadMoreTask();
        ThreadPoolFactory.getNormalThreadPool().getThreadPoolExecutor().execute(loadMoreTask);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position==getCount()-1?VIEW_TYPE_LOAD_MORE:VIEW_TYPE_NORMAL;
    }

    class LoadMoreTask implements Runnable {

        private int mState;
        private List<SearchResponseBean.ResponseDataBean.SearchlistBean> mMoreData;

        @Override
        public void run() {
            mState = LoadMoreHolder.STATE_LOADING;
            mCurrentPage++;
            try {
                mMoreData = RetrofitClient.getInstance().getTubeService().search(mKeyword, mCurrentPage).execute().body().response_data.searchlist;
                if (mMoreData == null) {
                    mState = LoadMoreHolder.STATE_NONE;
                } else {
                    if (mMoreData.size() < Constants.PAGE_SIZE) {
                        mState = LoadMoreHolder.STATE_NONE;
                    } else {
                        mState = LoadMoreHolder.STATE_LOADING;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.STATE_RETRY;
            }

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    isLoadingMore = false;
                    mLoadMoreHolder.refreshUI(mState);
                    if (mMoreData != null) {
                        mSearchlistBeen.addAll(mMoreData);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    static class ViewHolder {
        @InjectView(R.id.itemThumbnailView)
        ImageView mItemThumbnailView;
        @InjectView(R.id.itemDurationView)
        TextView mItemDurationView;
        @InjectView(R.id.itemThumbnailViewContainer)
        RelativeLayout mItemThumbnailViewContainer;
        @InjectView(R.id.itemVideoTitleView)
        TextView mItemVideoTitleView;
        @InjectView(R.id.itemUploaderView)
        TextView mItemUploaderView;
        @InjectView(R.id.itemUploadDateView)
        TextView mItemUploadDateView;
        @InjectView(R.id.itemViewCountView)
        TextView mItemViewCountView;
        @InjectView(R.id.cv_video_item)
        CardView mCVVideoItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mCVVideoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
