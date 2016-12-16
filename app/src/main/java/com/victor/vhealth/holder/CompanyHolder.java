package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.CompanyInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 药店药房的ViewHolder
 * Created by Victor on 2016/12/13.
 */
public class CompanyHolder extends BaseHolder<CompanyInfo> {

    @ViewInject(R.id.iv_list_company)
    private ImageView mIvcompany;
    @ViewInject(R.id.tv_list_company)
    private TextView mTvName;
    @ViewInject(R.id.tv_list_company_add)
    private TextView mTvAddress;


    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_company_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(CompanyInfo data) {

        mTvName.setText(data.name);
        mTvAddress.setText("地址：" + data.address);
        BitmapHelper.display(mIvcompany, Constant.URL.IMG_BASE + data.img);
    }
}
