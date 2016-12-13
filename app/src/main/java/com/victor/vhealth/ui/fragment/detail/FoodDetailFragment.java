package com.victor.vhealth.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.FoodInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.FoodDetailProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/** 食品详情页面fragment
 * Created by Victor on 2016/12/13.
 */
public class FoodDetailFragment extends ContentBaseFragment {

    @ViewInject(R.id.tv_food_detail_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_food_detail_keyword)
    private TextView mTvKeyWord;
    @ViewInject(R.id.iv_food_detail)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_food_message)
    private TextView mTvMessage;
    private FoodInfo mFoodInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        FoodDetailProtocol protocol = new FoodDetailProtocol(Constant.URL.LIFE_FOOD, mId);
        try {
            mFoodInfo = protocol.loadData(0);
            return checkState(mFoodInfo);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_food_detail, null);
        ViewUtils.inject(this, view);

        BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + mFoodInfo.img);
        mTvName.setText(mFoodInfo.name);
        mTvKeyWord.setText(mFoodInfo.keywords);
        mTvMessage.setText(Html.fromHtml(mFoodInfo.message));

        return view;
    }
}
