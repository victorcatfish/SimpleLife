package com.victor.vhealth.ui.fragment.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DrugDetailProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/** 药品信息详细界面fragment
 * Created by Victor on 2016/11/21.
 */
public class DrugDetailFragment extends ContentBaseFragment {

    @ViewInject(R.id.iv_drug_detail)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_drug_detail_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_drug_detail_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_drug_message)
    private TextView mTvMessage;
    @ViewInject(R.id.ll_factory)
    private LinearLayout mLlFactory;

    private DrugInfo mDrugInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        DrugDetailProtocol protocol = new DrugDetailProtocol(mId, Constant.URL.MEDICINE_DRUG);
        try {
            mDrugInfo = protocol.loadData(1);
            return checkState(mDrugInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }

    }

    @Override
    protected View initSuccessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_drug_detail, null);
        ViewUtils.inject(this, view);

        BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + mDrugInfo.img);
        mTvName.setText(mDrugInfo.name);
        mTvMessage.setText(Html.fromHtml(mDrugInfo.message));
        mTvType.setText(mDrugInfo.type);

        /**
         * <TextView
         android:id="@+id/tv_drug_factory_name"
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:layout_margin="5dp"
         android:padding="5dp"
         android:textColor="@color/dark_gray"
         android:textSize="12sp"
         android:text="企业名称"/>
         */

        if (mDrugInfo.numbers != null && mDrugInfo.numbers.size() > 0) {
            for (int i = 0; i < mDrugInfo.numbers.size(); i++) {
                TextView tvFactoryName = new TextView(UIUtils.getContext());
                int paddding = UIUtils.dip2px(5);
                tvFactoryName.setPadding(paddding, paddding, paddding, paddding);
                tvFactoryName.setTextColor(Color.rgb(10, 10, 10));
                tvFactoryName.setTextSize(14);
                tvFactoryName.setText("【企业名称】 " + mDrugInfo.numbers.get(i).factory);
                TextView tvNumber = new TextView(UIUtils.getContext());
                tvNumber.setPadding(paddding, paddding, paddding, UIUtils.dip2px(10));
                tvNumber.setTextColor(Color.rgb(10, 10, 10));
                tvNumber.setTextSize(14);
                tvNumber.setText("【批准文号】 " + mDrugInfo.numbers.get(i).number);

                mLlFactory.addView(tvFactoryName);
                mLlFactory.addView(tvNumber);
            }
        }
        return view;
    }
}
