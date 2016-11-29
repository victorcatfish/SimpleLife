package com.victor.vhealth.ui.fragment.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.base.KeyWordSearchBaseFragment;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.DrugSearchHolder;
import com.victor.vhealth.protocol.DrugKeywordSearchProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 药物信息关键词搜索fragment
 * Created by Victor on 2016/11/28.
 */
public class DrugKeywordSearchFragment extends KeyWordSearchBaseFragment {


    @ViewInject(R.id.lv_medicine_search)
    private ListView mLvDrug;

    private DrugKeywordSearchProtocol mProtocol;
    private List<DrugInfo> mDrugInfos;

    @Override
    protected LoadingPager.LoadResult initData() {

        mProtocol = new DrugKeywordSearchProtocol(mClassifyName, mKeyWord);
        try {
            mDrugInfos = mProtocol.loadData(1);
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
        DrugKeySearchAdapter adapter = new DrugKeySearchAdapter(mDrugInfos, UIUtils.getContext());
        mLvDrug.setOnItemClickListener(adapter);
        mLvDrug.setAdapter(adapter);
        return view;
    }


    class DrugKeySearchAdapter extends CustomBaseAdapter<DrugInfo> {

        public DrugKeySearchAdapter(List<DrugInfo> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected BaseHolder<DrugInfo> getHolderInstance() {
            return new DrugSearchHolder();
        }

        @Override
        protected List<DrugInfo> onLoadMore() throws Exception {
            return mProtocol.loadData(mDrugInfos.size() / 20 + 1);
        }


        @Override
        protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            intent.putExtra(ContentBaseFragment.DATA_ID, (int) mDrugInfos.get(position).id);
            // 放入url里面的关键词，用于区分是药物、医院信息等……
            intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DRUG);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);
        }
    }
}
