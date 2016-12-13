package com.victor.vhealth.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.domain.PicInfo;
import com.victor.vhealth.factory.ThreadPoolFactory;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.LoadMoreHolder;
import com.victor.vhealth.protocol.PicProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/**
 * Created by Victor on 2016/12/9.
 */
public class PicAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_LOAD_MORE = 0;
    private static final int VIEW_TPYE_NORMAL = 1;

    private int mCurLoadMoreState = LoadMoreViewHolder.STATE_NONE;
    private LoadMoreViewHolder mLoadMoreHolder;
    private List<PicInfo> mPicInfos;
    private PicProtocol mPicProtocol;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public PicAdapter(List<PicInfo> picInfos, PicProtocol protocol) {
        mPicInfos = picInfos;
        mPicProtocol = protocol;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根布局
        if (viewType == VIEW_TPYE_NORMAL) {
            return new ItemViewHolder(View.inflate(UIUtils.getContext(), R.layout.pic_list_item, null));
        } else {
            return getLoadMoreHolder();
        }
    }

    private LoadMoreViewHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreViewHolder(View.inflate(UIUtils.getContext(), R.layout.item_load_more, null));
        }
        return mLoadMoreHolder;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE;
        }
        return VIEW_TPYE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { // 填充数据
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder)holder).setDataAndRefreshUI(mPicInfos.get(position), position);
        } else if (holder instanceof LoadMoreViewHolder) {
            performLoadMore();
            ((LoadMoreViewHolder) holder).refreshView(mCurLoadMoreState);
        }

    }

    @Override
    public int getItemCount() {
        if (mPicInfos != null && mPicInfos.size() > 0) {
            return mPicInfos.size();
        }
        return 0;
    }



    /**执行加载更多操作*/
    private void performLoadMore() {
        // 防止重复加载
        if (mCurLoadMoreState == LoadMoreHolder.STATE_LOADING) {
            return;
        }
        mCurLoadMoreState = LoadMoreHolder.STATE_LOADING;
        mLoadMoreHolder.refreshView(mCurLoadMoreState);
        ThreadPoolFactory.getThreadPool().execute(new LoadMoreTask());
    }

    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            List<PicInfo> tempData = null;
            try {
                tempData = onLoadMore();
                if (tempData == null) {
                    // 说明没有更多数据了
                    mCurLoadMoreState = LoadMoreHolder.STATE_NO_DATA;
                } else {
                    mCurLoadMoreState = LoadMoreHolder.STATE_NONE;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mCurLoadMoreState = LoadMoreHolder.STATE_RETRY;
            }

            // 根据状态刷新界面
            final List<PicInfo> data = tempData;

            UIUtils.postTaskOnUIThread(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreHolder.refreshView(mCurLoadMoreState);
                    // 如果加载到数据 更新数据并刷新列表
                    if (data != null) {
                        mPicInfos.addAll(data);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private List<PicInfo> onLoadMore() throws Exception {
        return mPicProtocol.loadData(mPicInfos.size() / PicProtocol.PAGE_SIZE + 1);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tv_list_pic)
        private TextView mPicTv;
        @ViewInject(R.id.iv_list_pic)
        private ImageView mPicIv;

        private View mItemView;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            mItemView = itemView;
            ViewUtils.inject(this, itemView);
        }

        public void setDataAndRefreshUI(PicInfo picInfo, final int position) {
            mPicTv.setText(picInfo.title);
            mPicIv.setImageResource(R.mipmap.pic_default);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(mItemView, position);
                }
            });
            BitmapHelper.display(mPicIv, Constant.URL.IMG_BASE + picInfo.img);
        }
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public static final int STATE_LOADING = 0;
        public static final int STATE_RETRY = 1;
        public static final int STATE_NONE = 2;
        public static final int STATE_NO_DATA = 3;

        @ViewInject(R.id.item_loadmore_container_loading)
        LinearLayout mContainerLoadMore;
        @ViewInject(R.id.item_loadmore_container_retry)
        LinearLayout mContainerRetry;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }

        public void refreshView(Integer state) {
            mContainerLoadMore.setVisibility(View.GONE);
            mContainerRetry.setVisibility(View.GONE);
            switch (state) {
                case STATE_LOADING:// 加载更多中
                    mContainerLoadMore.setVisibility(View.VISIBLE);
                    break;
                case STATE_RETRY: // 加载更多失败，请重试
                    mContainerRetry.setVisibility(View.VISIBLE);
                    break;
                case STATE_NO_DATA: // 没有更多数据加载了
                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_NONE:
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int position);
    }

}
