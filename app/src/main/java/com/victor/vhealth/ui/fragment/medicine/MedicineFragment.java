package com.victor.vhealth.ui.fragment.medicine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by Victor on 2016/11/24.
 */
public abstract class MedicineFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        addChildFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    /**初始化布局*/
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**刷新布局，初始化fragment在此方法里面实现*/
    protected void addChildFragment() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }
}
