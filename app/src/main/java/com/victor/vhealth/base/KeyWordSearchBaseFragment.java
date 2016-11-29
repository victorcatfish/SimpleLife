package com.victor.vhealth.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/** 关键词搜索的Fragment基类
 * Created by Victor on 2016/11/28.
 */
public abstract class KeyWordSearchBaseFragment extends ContentBaseFragment {

    public static final String KEYWORD_SEARCH_CLASSIFY = "keyword_search_classify";
    public static final String KEY_WORD = "keyword";

    public String mClassifyName;
    public String mKeyWord;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mClassifyName = arguments.getString(KEYWORD_SEARCH_CLASSIFY);
            mKeyWord = arguments.getString(KEY_WORD);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }
}
