package com.victor.vhealth.ui.fragment.medicine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.adapter.DiseaseAapter;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DiseaseProtocol;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 受关注的热点疾病fragment
 * Created by Victor on 2016/11/24.
 */
public class DiseaseFocusFragment extends ContentBaseFragment {

    @ViewInject(R.id.gv_drug_hot_recent)
    private GridView mGvDiseaseList;
    private List<DiseaseInfo> mDiseaseInfos;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        DiseaseProtocol diseaseProtocol = new DiseaseProtocol(Constant.URL.MEDICINE_DISEASE_LIST, -1);
        try {
            mDiseaseInfos = diseaseProtocol.loadData(1);
            return checkState(mDiseaseInfos);
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

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_hot_recent_drug, null);
        ViewUtils.inject(this, view);
        DiseaseAapter adapter = new DiseaseAapter(mDiseaseInfos, UIUtils.getContext());
        mGvDiseaseList.setAdapter(adapter);
        mGvDiseaseList.setOnItemClickListener(adapter);

        return view;
    }
}
