package com.victor.vhealth.ui.fragment.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.KeyWordSearchBaseFragment;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.ui.activity.KeywordSearchActivity;
import com.victor.vhealth.util.UIUtils;

/** 药物信息fragment
 * Created by Victor on 2016/11/19.
 */
public class DrugFragment extends MedicineFragment implements View.OnClickListener {

    @ViewInject(R.id.et_drug_search)
    private EditText mEtDrugSearch;
    @ViewInject(R.id.iv_drug_search)
    private ImageView mIvDrugSearch;
    private DrugClassfiyFragment mFragment;


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void addChildFragment() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fl_drug_classify, new DrugClassfiyFragment());
        ft.add(R.id.fl_drug_hot, new DrugHotFragment());
        ft.add(R.id.fl_drug_recent, new DrugRecentFragment());
        ft.commit();
    }

    @Override
    protected void initListener() {
        mIvDrugSearch.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_drug_search:
                searchDrug();
                break;
        }
    }

    private void searchDrug() {
        String keyWord = mEtDrugSearch.getText().toString().trim();
        if (!keyWord.isEmpty()) {
            Intent intent = new Intent(UIUtils.getContext(), KeywordSearchActivity.class);
            intent.putExtra(KeyWordSearchBaseFragment.KEYWORD_SEARCH_CLASSIFY, Constant.SearchKeyword.DRUG);
            intent.putExtra(KeyWordSearchBaseFragment.KEY_WORD, keyWord);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
