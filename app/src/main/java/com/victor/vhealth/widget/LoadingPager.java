package com.victor.vhealth.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.victor.vhealth.R;
import com.victor.vhealth.factory.ThreadPoolFactory;
import com.victor.vhealth.util.LogUtils;
import com.victor.vhealth.util.UIUtils;

/**
 * Created by Victor on 2016/7/5.
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_UNDO = -1;// 初始状态
    public static final int STATE_LOADING = 0;// 加载状态
    public static final int STATE_EMPTY = 1;// 空状态
    public static final int STATE_ERROR = 2;// 加载错误状态
    public static final int STATE_SUCCESS = 3;// 加载成功状态

    private int mCurState = STATE_UNDO;

    private View mLoadingPage;
    private View mEmptyPage;
    private View mErrorPage;
    private View mSuccessPage;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initExceptionView();
    }

    private void initExceptionView() {

        // 初始化加载页面
        if (mLoadingPage == null) {
            mLoadingPage = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
            this.addView(mLoadingPage);
        }
        // 初始化空页面
        if (mEmptyPage == null) {
            mEmptyPage = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
            this.addView(mEmptyPage);
        }

        // 初始化错误页面
        if (mErrorPage == null) {
            mErrorPage = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
            mErrorPage.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 重新触发加载数据数据
                    loadData();
                }
            });
            this.addView(mErrorPage);
        }

        refreshUIbyState();
    }

    /**根据加载结果的状态刷新界面*/
    private void refreshUIbyState() {

        // this.removeAllViews();

        // 控制加载视图的显示和隐藏
        mLoadingPage.setVisibility(mCurState == STATE_LOADING || mCurState == STATE_UNDO
                ? View.VISIBLE : View.GONE);
        // 控制空视图的显示和隐藏
        mEmptyPage.setVisibility(mCurState == STATE_EMPTY ? View.VISIBLE : View.GONE);
        // 控制加载错误视图的显示和隐藏
        mErrorPage.setVisibility(mCurState == STATE_ERROR ? View.VISIBLE : View.GONE);

        if (mSuccessPage == null && mCurState == STATE_SUCCESS) {
            mSuccessPage = initSuccessView();
            if (mSuccessPage != null) {
                this.addView(mSuccessPage);
            }
        }
        // 不能省略 如果省略，BaseFragment第二次使用LoadingPager的时候，复用之前的，
        // 如果之前是成功，复用的时候是失败，复用的时候会显示之前成功的视图
        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    /**在加载数据成功之后，调用refreshUIbyState()方法刷新UI的时候调用*/
    protected abstract View initSuccessView();

    /**执行加载数据操作*/
    public void loadData() {
        if (mCurState != STATE_LOADING && mCurState != STATE_SUCCESS) {
            mCurState = STATE_LOADING;
            refreshUIbyState();
            //            new Thread(new LoadTask()).start();
            ThreadPoolFactory.getThreadPool().execute(new LoadTask());
        }
    }

    private class LoadTask implements Runnable {

        @Override
        public void run() {
            LoadResult state = initData();
            mCurState = state.getState();
            UIUtils.postTaskOnUIThread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.sf("refreshUIbyState");
                    refreshUIbyState();
                }
            });
        }
    }

    /**具体加载数据的过程操作，由子类具体实现*/
    protected abstract LoadResult initData();

    public enum LoadResult {

        SUCCESS(STATE_SUCCESS),
        ERROR(STATE_ERROR),
        EMPTY(STATE_EMPTY);

        int state;
        public int getState() {
            return state;
        }

        LoadResult(int state) {
            this.state = state;
        }
    }
}
