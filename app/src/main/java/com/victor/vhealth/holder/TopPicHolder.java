package com.victor.vhealth.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.domain.TopPicInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.TopPicViewPager;

import java.util.List;

/**顶部滑动图片ViewHolder
 * Created by Victor on 2016/7/8.
 */
public class TopPicHolder extends BaseHolder<List<TopPicInfo>> {

    @ViewInject(R.id.vp_top_picture)
    TopPicViewPager mVpTopPic;
    @ViewInject(R.id.ll_top_pic_indicator)
    LinearLayout mPicIndicatorContainer;
    @ViewInject(R.id.tv_top_pic_title)
    TextView mTvTopPicTitle;

    private List<TopPicInfo> mDatas;
    private int mPrePosition;
    public AutoScrollTask mAutoScrollTask;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.layout_top_picture, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void refreshView(List<TopPicInfo> data) {
        mDatas = data;
        // 添加页面小圆点指示器
        for (int i = 0; i < data.size(); i++) {
            View indicatorView = new View(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (UIUtils.dip2px(8), UIUtils.dip2px(8));
            if (i != 0) {
                indicatorView.setBackgroundResource(R.mipmap.indicator_normal);
                // 设置左边距
                params.leftMargin = UIUtils.dip2px(5);
            } else {
                indicatorView.setBackgroundResource(R.mipmap.indicator_selected);
            }
            mPicIndicatorContainer.addView(indicatorView, params);
        }

        TopPicPagerAdapter adapter = new TopPicPagerAdapter();
        mVpTopPic.setAdapter(adapter);

        // 开启无限轮播
        startScroll();

        mVpTopPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 更新指示器的状态
                position = position % mDatas.size();
                View view = mPicIndicatorContainer.getChildAt(position);
                // ※ 将前一个位置的小圆点置为正常状态必须在设置选中小圆点状态之前，
                // 否则第一次进入的时候，会造成第一个小圆点为灰色(非选中状态)
                mPicIndicatorContainer.getChildAt(mPrePosition)
                        .setBackgroundResource(R.mipmap.indicator_normal);
                view.setBackgroundResource(R.mipmap.indicator_selected);
                mPrePosition = position;
                mTvTopPicTitle.setText(mDatas.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 设置初始位置为50万的中间，方便左右“无限”滑动
        mVpTopPic.setCurrentItem(Constant.TOP_PIC_PAGER_SIZE / 2);

        // 监听触摸事件 当用户触摸图片位置时候 停止自动轮播
        mVpTopPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 停止自动轮播
                        stopScroll();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // 重新开始轮播
                        startScroll();
                        break;
                }
                return false;
            }
        });

    }

    /**开启轮播*/
    public void startScroll() {
        if (mAutoScrollTask == null) {
            mAutoScrollTask = new AutoScrollTask();
        }
        mAutoScrollTask.start();
    }

    /**停止轮播*/
    public void stopScroll() {
        if (mAutoScrollTask == null) {
            mAutoScrollTask.stop();
        }
    }

    class TopPicPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mDatas != null) {
                return Constant.TOP_PIC_PAGER_SIZE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mDatas.size();
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String imgUrl = Constant.URL.IMG_BASE + mDatas.get(position).imgUrl;
            BitmapHelper.display(imageView, imgUrl);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class AutoScrollTask implements Runnable {

        public void start() {
            UIUtils.getMainThreadHandler().removeCallbacksAndMessages(null);
            UIUtils.postTaskDelay(this, 3000);
        }
        @Override
        public void run() {
            int currentItem = mVpTopPic.getCurrentItem();
            currentItem++;
            mVpTopPic.setCurrentItem(currentItem);
            start();
        }

        public void stop() {
            UIUtils.removeTask(this);
        }
    }
}
