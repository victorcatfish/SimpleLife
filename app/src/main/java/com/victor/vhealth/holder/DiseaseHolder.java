package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 疾病信息列表的ViewHolder
 * Created by Victor on 2016/11/24.
 */
public class DiseaseHolder extends BaseHolder<DiseaseInfo> {

    @ViewInject(R.id.iv_medicine_hot)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_medicine_hot_title)
    private TextView mTvName;
    @ViewInject(R.id.tv_medicne_hot_tag)
    private TextView mTvKeyWorld;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_hot_medicine, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(DiseaseInfo data) {
        String startImgUrl = data.img.substring(0, 4);
        if (startImgUrl.equals("http")) {
            BitmapHelper.display(mIvImg, data.img);
        } else {
            BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + data.img);
        }
        mTvName.setText(data.name);
        mTvKeyWorld.setText(data.keywords);
    }
}
