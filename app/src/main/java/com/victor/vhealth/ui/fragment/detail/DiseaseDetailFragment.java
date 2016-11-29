package com.victor.vhealth.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DiseaseDetailProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/** 药品信息详细界面fragment
 * Created by Victor on 2016/11/21.
 */
public class DiseaseDetailFragment extends ContentBaseFragment {

    @ViewInject(R.id.iv_disease_detail)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_disease_detail_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_disease_detail_keyword)
    private TextView mTvKeyword;
    @ViewInject(R.id.tv_disease_message)
    private TextView mTvMessage;
    @ViewInject(R.id.tv_disease_reason)
    private TextView mTvReason;
    @ViewInject(R.id.tv_disease_symptom)
    private TextView mTvSymptom;
    @ViewInject(R.id.tv_disease_check)
    private TextView mTvCheck;
    @ViewInject(R.id.tv_disease_care)
    private TextView mTvCare;
    @ViewInject(R.id.tv_disease_drug)
    private TextView mTvDrug;

    private DiseaseInfo mDiseaseInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        DiseaseDetailProtocol protocol = new DiseaseDetailProtocol(mId, Constant.URL.MEDICINE_DISEASE);
        try {
            mDiseaseInfo = protocol.loadData(1);
            return checkState(mDiseaseInfo);
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

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_disease_detail, null);
        ViewUtils.inject(this, view);

        BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + mDiseaseInfo.img);
        mTvName.setText(mDiseaseInfo.name);
        mTvKeyword.setText(mDiseaseInfo.keywords);
        mTvReason.setText(Html.fromHtml(mDiseaseInfo.causetext));
        mTvSymptom.setText(Html.fromHtml(mDiseaseInfo.symptomtext));
        mTvCheck.setText(Html.fromHtml(mDiseaseInfo.checktext));
        mTvCare.setText(Html.fromHtml(mDiseaseInfo.caretext));
        mTvDrug.setText(Html.fromHtml(mDiseaseInfo.drug));
        mTvMessage.setText(Html.fromHtml(mDiseaseInfo.message));

        return view;
    }
}
