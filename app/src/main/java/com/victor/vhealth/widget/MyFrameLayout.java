package com.victor.vhealth.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.victor.vhealth.util.UIUtils;

/**
 * Created by Victor on 2016/11/28.
 */
public class MyFrameLayout extends FrameLayout {

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int windowWidth = UIUtils.getWindowWidth();
        getChildAt(1).layout(left + windowWidth, top, right + windowWidth, bottom);
    }
}
