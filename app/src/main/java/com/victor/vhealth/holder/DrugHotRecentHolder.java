package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 药品页面的 最新药品和 最热药品模块的ViewHolder
 * Created by Victor on 2016/11/28.
 */
public class DrugHotRecentHolder extends BaseHolder<DrugInfo> {

    @ViewInject(R.id.iv_medicine_hot)
    private ImageView mIvIcon;
    @ViewInject(R.id.tv_medicne_hot_tag)
    private TextView mTvTag;
    @ViewInject(R.id.tv_medicine_hot_title)
    private TextView mTvName;


    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_hot_medicine, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(DrugInfo data) {
        mTvName.setText(data.name);
        mTvTag.setText(data.tag);
        BitmapHelper.display(mIvIcon, Constant.URL.IMG_BASE + data.img);
    }
}
