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

/**
 * Created by Victor on 2016/11/28.
 */
public class DrugSearchHolder extends BaseHolder<DrugInfo> {

    @ViewInject(R.id.iv_medicine_search)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_medicine_search_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_medicine_msg)
    private TextView mTvMsg;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_medicine_search, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(DrugInfo data) {
        String startImgUrl = data.img.substring(0, 4);
        if (startImgUrl.equals("http")) {
            BitmapHelper.display(mIvImg, data.img);
        } else {
            BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + data.img);
        }
        mTvName.setText(data.name);
        mTvMsg.setText(data.description);
    }
}
