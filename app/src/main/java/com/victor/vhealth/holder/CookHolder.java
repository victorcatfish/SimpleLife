package com.victor.vhealth.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.CookInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

/** 食品栏目的ViewHolder
 * Created by Victor on 2016/12/13.
 */
public class CookHolder extends BaseHolder<CookInfo> {

    @ViewInject(R.id.iv_list_food)
    private ImageView mIvFood;
    @ViewInject(R.id.tv_list_food)
    private TextView mTvFood;


    @Override
    protected View initView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.item_food_list, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(CookInfo data) {

        mTvFood.setText(data.name);
        BitmapHelper.display(mIvFood, Constant.URL.IMG_BASE + data.img);

    }
}
