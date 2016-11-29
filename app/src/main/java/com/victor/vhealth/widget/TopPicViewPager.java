package com.victor.vhealth.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**顶部图片的ViewPager 自己实现左右滑动事件处理
 * Created by Victor on 2016/7/8.
 */
public class TopPicViewPager extends ViewPager {

    private float mStartX;
    private float mStartY;

    public TopPicViewPager(Context context) {
        this(context, null);
    }

    public TopPicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = getX();
                mStartY = getY();
                break;
            case  MotionEvent.ACTION_MOVE:
                float endX = getX();
                float endY = getY();
                // 计算左右滑动的距离
                float deltaX = endX - mStartX;
                // 计算上下滑动的距离
                float deltaY = endY - mStartY;
                // 如果左右滑动的距离大于上下滑动的距离 判定为左右滑动
                if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                    // 左右滑动 自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
