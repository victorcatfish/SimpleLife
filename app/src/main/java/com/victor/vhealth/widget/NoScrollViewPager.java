package com.victor.vhealth.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**不能滑动的ViewPager
 * Created by Victor on 2016/7/4.
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 让其不拦截控件的滑动事件,从而将事件交给子控件处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    // 重写此方法 但是不具体实现，从而实现对滑动事件的禁用
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
