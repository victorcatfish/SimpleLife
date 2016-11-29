package com.victor.vhealth.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/** 搜索基础fragment
 * Created by Victor on 2016/11/25.
 */
public abstract class ClassifySearchBaseFragment extends ContentBaseFragment {

    public static final String SEARCH_CLASSIFY_KEY = "search_classify_key";

    public String mSearchKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mSearchKey = arguments.getString(SEARCH_CLASSIFY_KEY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }
}
