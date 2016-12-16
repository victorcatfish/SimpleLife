package com.victor.vhealth.ui.fragment.detail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.HospitalDetailProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/** 食品详情页面fragment
 * Created by Victor on 2016/12/13.
 */
public class HospitalDetailFragment extends ContentBaseFragment {

    @ViewInject(R.id.tv_hospital_detail_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_hospital_level)
    private TextView mTvLevel;
    @ViewInject(R.id.tv_hospital_tel)
    private TextView mTvTel;
    @ViewInject(R.id.tv_hospital_add)
    private TextView mTvAddress;
    @ViewInject(R.id.iv_hospital_detail)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_hospital_message)
    private TextView mTvMessage;
    @ViewInject(R.id.rl_detail_toggle)
    private RelativeLayout mRlToggle;
    @ViewInject(R.id.iv_arrow)
    private ImageView mIvArrow;

    private HospitalInfo mHospitalInfo;
    private boolean isOpen = false;
    private LinearLayout.LayoutParams mParams;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        HospitalDetailProtocol protocol = new HospitalDetailProtocol(Constant.URL.MEDICINE_HOSPTIAL, mId);
        try {
            mHospitalInfo = protocol.loadData(0);
            return checkState(mHospitalInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }

    }

    @Override
    protected View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_hospital_detail, null);
        ViewUtils.inject(this, view);

        mRlToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + mHospitalInfo.img);
        mTvName.setText(mHospitalInfo.name);
        mTvLevel.setText(mHospitalInfo.level);
        mTvTel.setText("电话：" + mHospitalInfo.tel);
        mTvAddress.setText("地址：" + mHospitalInfo.address);
        if ("".equals(mHospitalInfo.message)) {
            mTvMessage.setText("暂无简介");
        } else {
            mTvMessage.setText(Html.fromHtml(mHospitalInfo.message));
            mTvMessage.post(new Runnable() {
                @Override
                public void run() {
                    // 默认展示7行的高度
                    int shortHeight = getShortHeight();
                    mParams = (LinearLayout.LayoutParams)
                            mTvMessage.getLayoutParams();
                    mParams.height = shortHeight;
                    mTvMessage.setLayoutParams(mParams);
                }
            });
        }
        return view;
    }

    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        isOpen = !isOpen;
        // 更新箭头
        if (isOpen) {
            mIvArrow.setImageResource(R.mipmap.arrow_up);
        } else {
            mIvArrow.setImageResource(R.mipmap.arrow_down);
        }
        if (longHeight <= shortHeight) {
            return;
        }
        ValueAnimator animator;
        if (!isOpen) {
            // 关闭操作
            animator = ValueAnimator.ofInt(longHeight, shortHeight);
        } else {
            // 打开操作
            animator = ValueAnimator.ofInt(shortHeight, longHeight);
        }


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                mParams.height = height;
                mTvMessage.setLayoutParams(mParams);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // ScrollView滑动到底部
                final ScrollView scrollView = getScrollView();
                // 问了运行更安全和稳定，将滑动post到消息队列中
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setDuration(200);
        animator.start();

    }

    public ScrollView getScrollView() {
        ViewParent parent = mTvMessage.getParent();
        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView) parent;
    }


    /**获取4行文本的TextView的高度*/
    private int getShortHeight() {
        // 模拟一个TextView设置最大行数为7行，然后计算该模拟的TextView的高度
        int width = mTvMessage.getMeasuredWidth();
        TextView view = new TextView(UIUtils.getContext());
        // 设置和需要模拟的TextView的文字大小一致
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        view.setMaxLines(4);
        view.setText(Html.fromHtml(mHospitalInfo.message));

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        // 高度包裹内容，包裹内容的情况 参1：代表尺寸的最大值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

    /**获取TextView的完整高度*/
    private int getLongHeight() {
        // 模拟一个TextView设置最大行数为7行，然后计算该模拟的TextView的高度
        int width = mTvMessage.getMeasuredWidth();
        TextView view = new TextView(UIUtils.getContext());
        // 设置和需要模拟的TextView的文字大小一致
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        view.setText(Html.fromHtml(mHospitalInfo.message));

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        // 高度包裹内容，包裹内容的情况 参1：代表尺寸的最大值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

}
