package com.victor.vhealth.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.util.UIUtils;

/**
 * Created by Victor on 2016/7/11.
 */
public abstract class DetailCommenFragment extends ContentBaseFragment implements View.OnClickListener {

    protected ImageView mIvFavo;
    protected ImageView mIvShare;
    protected Button mBtnSubmit;
    private FrameLayout mFlContent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    private WebView mWebView;

    public void goBack() {
        if (mWebView == null) {
            mWebView = getWebView();
        }
        if (mWebView != null) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                mActivity.finish();
            }
        }
    }

    @Override
    protected View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.layout_detail_commen, null);

        mFlContent = (FrameLayout) view.findViewById(R.id.fl_content_detail);
        mIvFavo = (ImageView) view.findViewById(R.id.iv_favo);
        mIvShare = (ImageView) view.findViewById(R.id.iv_share);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit_commend);

        View contentView = initView();
        if (contentView != null) {
            mFlContent.addView(contentView);
        }

        mBtnSubmit.setOnClickListener(this);
        mIvFavo.setOnClickListener(this);
        mIvShare.setOnClickListener(this);
        return view;
    }

    protected abstract View initView();

    protected abstract WebView getWebView();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_commend:
                UIUtils.showShortToast(UIUtils.getContext(), "发表评论");
                break;
            case R.id.iv_favo:
                UIUtils.showShortToast(UIUtils.getContext(), "收藏");
                break;
            case R.id.iv_share:
                UIUtils.showShortToast(UIUtils.getContext(), "分享");
                break;
        }
    }
}
