package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 食品栏目的ViewHolder
 * Created by Victor on 2016/12/13.
 */
public class HospitalHolder extends BaseHolder<HospitalInfo> {

    @ViewInject(R.id.iv_list_hospital)
    private ImageView mIvHospital;
    @ViewInject(R.id.tv_list_hospital)
    private TextView mTvHospital;
    @ViewInject(R.id.tv_list_hospital_add)
    private TextView mTvAddress;


    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_hospital_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(HospitalInfo data) {

        mTvHospital.setText(data.name);
        mTvAddress.setText("地址：" + data.address);
        BitmapHelper.display(mIvHospital, Constant.URL.IMG_BASE + data.img);
    }
}
