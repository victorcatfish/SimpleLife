package com.victor.vhealth.ui.fragment.medicine;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.KeyWordSearchBaseFragment;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.ui.activity.KeywordSearchActivity;
import com.victor.vhealth.util.UIUtils;

/** 疾病信息主页fragment
 * Created by Victor on 2016/11/24.
 */
public class DiseaseFragment extends MedicineFragment implements View.OnClickListener {

    @ViewInject(R.id.iv_disease_search)
    private ImageView mIvSearch;
    @ViewInject(R.id.et_disease_search)
    private EditText mEtDisease;
    @ViewInject(R.id.iv_disease_list)
    private ImageView mIvList;
    @ViewInject(R.id.ll_disease_right_menu)
    private LinearLayout mLlMenu;
    @ViewInject(R.id.sv_disease_main)
    private ScrollView mSvMain;

    public boolean isMenuState = false;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        return view;
    }

    @Override
    protected void addChildFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_disease_menu, new DiseaseMenuFragment());
        ft.add(R.id.fl_disease_focus, new DiseaseFocusFragment());
        ft.commitAllowingStateLoss();
    }


    @Override
    protected void initListener() {
        mIvList.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_disease_list:
                int windowWidth = UIUtils.getWindowWidth();
                ObjectAnimator.ofFloat(mLlMenu, "translationX", -windowWidth).setDuration(500).start();
                ObjectAnimator.ofFloat(mSvMain, "translationX", -windowWidth / 2).setDuration(500).start();
                isMenuState = true;
                break;
            case R.id.iv_disease_search:
                searchDisease();
                break;
        }
    }

    private void searchDisease() {
        String keyWord = mEtDisease.getText().toString().trim();
        if (!keyWord.isEmpty()) {
            Intent intent = new Intent(UIUtils.getContext(), KeywordSearchActivity.class);
            intent.putExtra(KeyWordSearchBaseFragment.KEYWORD_SEARCH_CLASSIFY, Constant.SearchKeyword.DISEASE);
            intent.putExtra(KeyWordSearchBaseFragment.KEY_WORD, keyWord);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public void hideMenu() {
        int windowWidth = UIUtils.getWindowWidth();
        ObjectAnimator.ofFloat(mSvMain, "translationX", 0).setDuration(500).start();
        ObjectAnimator.ofFloat(mLlMenu, "translationX", 0).setDuration(500).start();
        isMenuState = false;
    }

}
