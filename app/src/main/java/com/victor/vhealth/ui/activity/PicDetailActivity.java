package com.victor.vhealth.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.domain.PicInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.DetailPicProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

public class PicDetailActivity extends AppCompatActivity {

    public static final String PIC_ID = "pic_id";
    public static final String PIC_TITLE = "pic_title";
    public static final String PIC_SIZE = "pic_size";
    public static final int PAGE_CACHE_COUNT = 3;

    @ViewInject(R.id.fl_pic_content)
    private FrameLayout mFlPicContent;
    @ViewInject(R.id.tv_pic_title)
    private TextView mTvTitle;
    @ViewInject(R.id.ll_pic_indicator)
    private LinearLayout mPicIndicatorContainer;
    @ViewInject(R.id.vp_picture)
    private ViewPager mVpPic;

    private int mId;
    private String mTitle;
    private int mSize;
    private PicInfo mPicInfo;
    private int mPrePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        ViewUtils.inject(this);

        Intent intent = getIntent();
        mId = intent.getIntExtra(PIC_ID, -1);
        mTitle = intent.getStringExtra(PIC_TITLE);
        mSize = intent.getIntExtra(PIC_SIZE, -1);
        mTvTitle.setText(mTitle);

        // 初始化图片的指示器
        for (int i = 0; i < mSize; i++) {
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

        setDataAndRefreshView();
    }

    private void setDataAndRefreshView() {
        LoadingPager loadingPager = new LoadingPager(UIUtils.getContext()) {

            @Override
            protected LoadResult initData() {
                return PicDetailActivity.this.initData();
            }

            @Override
            protected View initSuccessView() {
                return PicDetailActivity.this.initSucessView();
            }
        };
        loadingPager.loadData();
        mFlPicContent.addView(loadingPager);
    }

    public LoadingPager.LoadResult initData() {
        DetailPicProtocol protocol = new DetailPicProtocol(Constant.URL.PIC_DETAIL, mId);
        try {
            mPicInfo = protocol.loadData(0);
            if (mPicInfo != null) {
                if (mPicInfo.list != null && mPicInfo.list.size() > 0) {
                    return LoadingPager.LoadResult.SUCCESS;
                } else {
                    return LoadingPager.LoadResult.EMPTY;
                }
            } else {
                return LoadingPager.LoadResult.ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    private View initSucessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.pic_detail_content_pager, null);
        ViewUtils.inject(this, view);
        mVpPic.setOffscreenPageLimit(PAGE_CACHE_COUNT);
        mVpPic.setAdapter(new PicDetailPagerAdapter());
        mVpPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                View view = mPicIndicatorContainer.getChildAt(position);
                // ※ 将前一个位置的小圆点置为正常状态必须在设置选中小圆点状态之前，
                // 否则第一次进入的时候，会造成第一个小圆点为灰色(非选中状态)
                mPicIndicatorContainer.getChildAt(mPrePosition)
                        .setBackgroundResource(R.mipmap.indicator_normal);
                view.setBackgroundResource(R.mipmap.indicator_selected);
                mPrePosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }



    class PicDetailPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPicInfo != null && mPicInfo.list != null && mPicInfo.list.size() > 0) {
                return mPicInfo.list.size();
            }
            return 0;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            String imgUrl = Constant.URL.IMG_BASE + mPicInfo.list.get(position).src;
            BitmapHelper.display(imageView, imgUrl);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
