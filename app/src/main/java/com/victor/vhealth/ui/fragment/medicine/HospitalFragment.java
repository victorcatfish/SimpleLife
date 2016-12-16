package com.victor.vhealth.ui.fragment.medicine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.adapter.HospitalAdapter;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.HospitalProtocol;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 医院门诊的Fragment
 * Created by Victor on 2016/12/14.
 */
public class HospitalFragment extends MedicineFragment {

    public static final int HOSPITAL_NULL_CLASSIFY_ID = -1;

    @ViewInject(R.id.lv_hospital)
    private ListView mLvHospital;

    private LoadingPager mLoadingPager;
    private HospitalProtocol mProtocol;
    private List<HospitalInfo> mDatas;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                protected View initSuccessView() {
                    return HospitalFragment.this.refreshView();
                }

                @Override
                protected LoadResult initData() {
                    return HospitalFragment.this.getData();
                }
            };
        }

        return mLoadingPager;
    }

    private LoadingPager.LoadResult getData() {

        mProtocol = new HospitalProtocol(HOSPITAL_NULL_CLASSIFY_ID, Constant.URL.MEDICINE_HOSPTIAL);
        try {
            mDatas = mProtocol.loadData(1);
            if (mDatas != null) {
                if (mDatas.size() > 0) {
                    return LoadingPager.LoadResult.SUCCESS;
                } else {
                    return LoadingPager.LoadResult.EMPTY;
                }
            } else {
                return LoadingPager.LoadResult.ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }

    }

    private View refreshView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_hospital, null);
        ViewUtils.inject(this, view);
        HospitalAdapter adapter = new HospitalAdapter(mDatas, UIUtils.getContext(), mProtocol);
        mLvHospital.setAdapter(adapter);
        mLvHospital.setOnItemClickListener(adapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoadingPager.loadData();
    }



}
