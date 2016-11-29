package com.victor.vhealth.ui.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

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

/** 医药搜索结果的fragment
 * Created by Victor on 2016/11/28.
 */
public class DrugSearchFragment extends ContentBaseFragment {

    @ViewInject(R.id.lv_medicine_search)
    private ListView mLvDrug;
    private List<DrugInfo> mDrugInfos;
    private DrugProtocol mDrugProtocol;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        mDrugProtocol = new DrugProtocol(mId, Constant.URL.MEDICINE_DRUG);
        try {
            mDrugInfos = mDrugProtocol.loadData(1);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_medicine_search, null);
        ViewUtils.inject(this, view);
        DrugAdapter adapter = new DrugAdapter(mDrugInfos, UIUtils.getContext(), DrugAdapter.ListType.LIST_VIEW_TYPE, mDrugProtocol);
        mLvDrug.setOnItemClickListener(adapter);
        mLvDrug.setAdapter(adapter);
        return view;
    }
}
