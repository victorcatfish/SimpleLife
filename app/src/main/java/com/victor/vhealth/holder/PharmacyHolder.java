package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.PharmacyInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 药店药房的ViewHolder
 * Created by Victor on 2016/12/13.
 */
public class PharmacyHolder extends BaseHolder<PharmacyInfo> {

    @ViewInject(R.id.iv_list_pharmacy)
    private ImageView mIvPharmacy;
    @ViewInject(R.id.tv_list_pharmacy)
    private TextView mTvName;
    @ViewInject(R.id.tv_list_pharmacy_add)
    private TextView mTvAddress;


    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_pharmacy_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(PharmacyInfo data) {

        mTvName.setText(data.name);
        mTvAddress.setText("地址：" + data.address);
        BitmapHelper.display(mIvPharmacy, Constant.URL.IMG_BASE + data.img);
    }
}
