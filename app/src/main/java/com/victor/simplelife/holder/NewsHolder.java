package com.victor.simplelife.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.simplelife.R;
import com.victor.simplelife.base.BaseHolder;
import com.victor.simplelife.domain.HealthInfo;
import com.victor.simplelife.global.Constant;
import com.victor.simplelife.util.BitmapHelper;
import com.victor.simplelife.util.StringUtils;
import com.victor.simplelife.util.UIUtils;

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
