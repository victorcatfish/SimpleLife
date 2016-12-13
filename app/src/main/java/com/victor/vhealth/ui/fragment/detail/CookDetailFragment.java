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
import com.victor.vhealth.domain.CookInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.CookDetailProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/** 食品详情页面fragment
 * Created by Victor on 2016/12/13.
 */
public class CookDetailFragment extends ContentBaseFragment {

    @ViewInject(R.id.tv_cook_detail_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_cook_detail_keyword)
    private TextView mTvKeyWord;
    @ViewInject(R.id.iv_cook_detail)
    private ImageView mIvImg;
    @ViewInject(R.id.tv_cook_message)
    private TextView mTvMessage;
    private CookInfo mCookInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoadingPager().loadData();
    }

    @Override
    protected LoadingPager.LoadResult initData() {

        CookDetailProtocol protocol = new CookDetailProtocol(Constant.URL.LIFE_COOKBOOK, mId);
        try {
            mCookInfo = protocol.loadData(0);
            return checkState(mCookInfo);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_cook_detail, null);
        ViewUtils.inject(this, view);

        BitmapHelper.display(mIvImg, Constant.URL.IMG_BASE + mCookInfo.img);
        mTvName.setText(mCookInfo.name);
        mTvKeyWord.setText(mCookInfo.keywords);
        mTvMessage.setText(Html.fromHtml(mCookInfo.message));

        return view;
    }
}
