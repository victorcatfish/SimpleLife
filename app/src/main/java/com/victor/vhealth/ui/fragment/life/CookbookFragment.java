package com.victor.vhealth.ui.fragment.life;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.widget.LoadingPager;

/**
 * Created by Victor on 2016/7/7.
 */
public class CookbookFragment extends ContentBaseFragment {


    @Override
    protected LoadingPager.LoadResult initData() {
        return LoadingPager.LoadResult.SUCCESS;
    }

    @Override
    protected View initSuccessView() {
        TextView textView = new TextView(getContext());
        textView.setText(getClass().getSimpleName() + "菜谱");
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
