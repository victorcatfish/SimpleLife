package com.victor.vhealth.ui.fragment.search;

import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.adapter.DiseaseSearchAdapter;
import com.victor.vhealth.base.ClassifySearchBaseFragment;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DiseaseProtocol;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 疾病信息搜索结果的fragment
 * Created by Victor on 2016/11/25.
 */
public class DiseaseClassifySearchFragment extends ClassifySearchBaseFragment {

    @ViewInject(R.id.lv_medicine_search)
    private ListView mLvSearchResult;
    private List<DiseaseInfo> mDiseaseInfos;
    private DiseaseProtocol mProtocol;

    @Override
    protected LoadingPager.LoadResult initData() {
        mProtocol = new DiseaseProtocol(Constant.URL.MEDICINE_DISEASE + mSearchKey, mId);
        try {
            mDiseaseInfos = mProtocol.loadData(1);
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

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_medicine_search, null);
        ViewUtils.inject(this, view);
        DiseaseSearchAdapter adapter = new DiseaseSearchAdapter(mDiseaseInfos, UIUtils.getContext(),mProtocol);
        mLvSearchResult.setAdapter(adapter);
        mLvSearchResult.setOnItemClickListener(adapter);

        return view;
    }
}
