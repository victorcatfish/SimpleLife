package com.victor.vhealth.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.victor.vhealth.factory.ThreadPoolFactory;
import com.victor.vhealth.holder.LoadMoreHolder;
import com.victor.vhealth.util.UIUtils;

import java.util.List;

/**
 * Created by Victor on 2016/7/5.
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener{

    private static final int VIEW_TYPE_LOAD_MORE = 0;
    private static final int VIEW_TPYE_NORMAL = 1;
    private final List<T> mDatas;
    private final Context mContext;
    private LoadMoreHolder mLoadMoreHolder;
    private int mCurLoadMoreState = LoadMoreHolder.STATE_NONE;

    public CustomBaseAdapter(List<T> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder;
        if (convertView == null) {
            if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
                holder = (BaseHolder<T>) getLoadMoreHolder();
            } else {
                holder = getHolderInstance();
            }
        } else {
            holder = (BaseHolder<T>) convertView.getTag();
        }

        if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
            if (needLoadMore()) {
                performLoadMore();
            } else {
                mLoadMoreHolder.setDataAndRefreshView(mCurLoadMoreState);
            }
        } else {
            holder.setDataAndRefreshView(mDatas.get(position));
        }
        return holder.mHolderView;
    }

    /**是否需要加载更多，默认返回true，子类可以根据自身需要重写*/
    protected boolean needLoadMore() {
        return true;
    }

    /**执行加载更多操作*/
    private void performLoadMore() {
        // 防止重复加载
        if (mCurLoadMoreState == LoadMoreHolder.STATE_LOADING) {
            return;
        }
        mCurLoadMoreState = LoadMoreHolder.STATE_LOADING;
        mLoadMoreHolder.setDataAndRefreshView(mCurLoadMoreState);
        ThreadPoolFactory.getThreadPool().execute(new LoadMoreTask());
    }

    /**获得加载更多的ViewHolder*/
    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    // 默认需要加载两种类型的View(一种常规，一种是加载更多)
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEW_TYPE_LOAD_MORE;
        }
        return VIEW_TPYE_NORMAL;
    }

    /**实例化具体的ViewHolder, 由子类具体实现*/
    protected abstract BaseHolder<T> getHolderInstance();

    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            List<T> tempData = null;
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
            final List<T> data = tempData;

            UIUtils.postTaskOnUIThread(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreHolder.setDataAndRefreshView(mCurLoadMoreState);
                    // 如果加载到数据 更新数据并刷新列表
                    if (data != null) {
                        mDatas.addAll(data);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**加载更多数据具体操作,默认返回null,子类根据自己情况具体实现*/
    protected List<T> onLoadMore() throws Exception{
        return null;
    }

    // 条目点击事件处理
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent instanceof AbsListView) {
            // 如果ListView有HeaderView 需要从数量中减去,更正positon
            if (parent instanceof ListView) {
                position = position - ((ListView) parent).getHeaderViewsCount();
            }
            if (getItemViewType(position) == VIEW_TYPE_LOAD_MORE) {
                performLoadMore();
            } else {
                onNormalItemClick(parent, view, position, id);
            }
        }
    }

    /** 普通列表条目点击事件的处理 为空实现 交由子类具体实现
     * @param parent 点击条目所在的父控件
     * @param view 被点击的条目
     * @param position 被点击条目所在索引
     * @param id 被点击条目id
     */
    protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
