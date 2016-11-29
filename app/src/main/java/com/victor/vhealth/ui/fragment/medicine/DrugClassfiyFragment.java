package com.victor.vhealth.ui.fragment.medicine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.ClassifyInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.ClassifyProtocol;
import com.victor.vhealth.ui.activity.SearchActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 药物信息模块 分类列表fragment
 * Created by Victor on 2016/11/18.
 */
public class DrugClassfiyFragment extends ContentBaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.gv_drug_classify)
    private GridView mGvDrugClassify;
    @ViewInject(R.id.iv_drug_classify_fold)
    private ImageView mIvFold;

    private List<ClassifyInfo> mClassifyInfos;

    private DrugClassifyAdapter mAdapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {
        ClassifyProtocol classifyProtocol = new ClassifyProtocol(Constant.URL.MEDICINE_DRUG);
        try {
            mClassifyInfos = classifyProtocol.loadData(1);
            return checkState(mClassifyInfos);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_drug_classify, null);
        ViewUtils.inject(this, view);
        mIvFold.setOnClickListener(this);
        mAdapter = new DrugClassifyAdapter();
        mAdapter.setCount(8);
        mGvDrugClassify.setAdapter(mAdapter);
        mGvDrugClassify.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_drug_classify_fold:
            if (mAdapter.getCount() == 8) {
                mAdapter.setCount(mClassifyInfos.size());
                mIvFold.setBackgroundResource(R.mipmap.icon_fold);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.setCount(8);
                mIvFold.setBackgroundResource(R.mipmap.icon_unfold);
                mAdapter.notifyDataSetChanged();
            }
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.gv_drug_classify:
                Intent intent = new Intent(UIUtils.getContext(), SearchActivity.class);
                intent.putExtra(ContentBaseFragment.DATA_ID, mClassifyInfos.get(position).id);
                intent.putExtra(SearchActivity.SEARCH_CALSSIFY_TYPE, Constant.URL.MEDICINE_DRUG);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);
                break;
        }
    }


    class DrugClassifyAdapter extends BaseAdapter {

        private int mCount;

        @Override
        public int getCount() {
            if (mClassifyInfos.size() > 8) {
                return mCount;
            } else {
                return mClassifyInfos.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return mClassifyInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(UIUtils.getContext());
            }
            ((TextView)convertView).setTextSize(12);
            ((TextView)convertView).setTextColor(Color.CYAN);
            ((TextView)convertView).setText(mClassifyInfos.get(position).name);

            return convertView;
        }

        public void setCount(int count) {
            mCount = count;
        }

    }
}
