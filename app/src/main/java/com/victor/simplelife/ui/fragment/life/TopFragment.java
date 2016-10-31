package com.victor.simplelife.ui.fragment.life;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.victor.simplelife.base.ContentBaseFragment;
import com.victor.simplelife.widget.LoadingPager;

/**
 * Created by Victor on 2016/7/7.
 */
public class TopFragment extends ContentBaseFragment {


    @Override
    protected LoadingPager.LoadResult initData() {
        return LoadingPager.LoadResult.SUCCESS;
    }

    @Override
    protected View initSuccessView() {
        TextView textView = new TextView(getContext());
        textView.setText(getClass().getSimpleName() + "热点");
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
