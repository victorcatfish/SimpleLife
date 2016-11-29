package com.victor.vhealth.ui.fragment.medicine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.adapter.DrugAdapter;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DrugProtocol;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 热门药物fragment
 * Created by Victor on 2016/11/20.
 */
public class DrugHotFragment extends ContentBaseFragment {

    private static final int HOT_GRUG_CLASS = 3;

    @ViewInject(R.id.gv_drug_hot_recent)
    private GridView mGvdrugList;
    private List<DrugInfo> mDrugInfos;
    private DrugProtocol mDrugProtocol;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {
        mDrugProtocol = new DrugProtocol(HOT_GRUG_CLASS, Constant.URL.MEDICINE_DRUG);
        try {
            mDrugInfos = mDrugProtocol.loadData(2);
            return checkState(mDrugInfos);
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
        DrugAdapter adapter = new DrugAdapter(mDrugInfos, UIUtils.getContext(), DrugAdapter.ListType.GRID_VIEW_TYPE, mDrugProtocol);
        mGvdrugList.setOnItemClickListener(adapter);
        mGvdrugList.setAdapter(adapter);
        return view;
    }

}
