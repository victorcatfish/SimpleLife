package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.HealthInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.StringUtils;
import com.victor.vhealth.util.UIUtils;

/**
 * Created by Victor on 2016/7/5.
 */
public class NewsHolder extends BaseHolder<HealthInfo> {

    @ViewInject(R.id.iv_news_icon)
    ImageView mIvIcon;
    @ViewInject(R.id.tv_news_title)
    TextView mTvTitle;
    @ViewInject(R.id.tv_news_des)
    TextView mTvDes;
    @ViewInject(R.id.tv_news_time)
    TextView mTvTime;
    @ViewInject(R.id.tv_news_rcount)
    TextView mTvRCount;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_news_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(HealthInfo data) {
        mTvTitle.setText(data.title);
        mTvDes.setText(data.description);
        mTvTime.setText(StringUtils.getStringTime(data.time));
        BitmapHelper.display(mIvIcon, Constant.URL.IMG_BASE + data.img);
        if (data.rcount > 0) {
            mTvRCount.setVisibility(View.VISIBLE);
            mTvRCount.setText(data.rcount + "");
        } else {
            mTvRCount.setVisibility(View.INVISIBLE);
        }
    }

}
