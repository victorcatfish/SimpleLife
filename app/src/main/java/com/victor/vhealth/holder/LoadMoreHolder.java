package com.victor.vhealth.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.util.UIUtils;

/**
 * Created by Victor on 2016/7/5.
 */
public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int STATE_LOADING = 0;
    public static final int STATE_RETRY = 1;
    public static final int STATE_NONE = 2;
    public static final int STATE_NO_DATA = 3;

    @ViewInject(R.id.item_loadmore_container_loading)
    LinearLayout mContainerLoadMore;
    @ViewInject(R.id.item_loadmore_container_retry)
    LinearLayout mContainerRetry;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(Integer state) {
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
