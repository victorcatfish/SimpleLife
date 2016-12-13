package com.victor.vhealth.ui.fragment.life;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.CookInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.CookHolder;
import com.victor.vhealth.protocol.CookProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 菜谱栏目的fragment
 * Created by Victor on 2016/7/7.
 */
public class CookbookFragment extends ContentBaseFragment {


    @ViewInject(R.id.gv_food)
    private GridView mGvFood;

    private List<CookInfo> mDatas;
    private CookProtocol mProtocol;

    @Override
    protected LoadingPager.LoadResult initData() {

        mProtocol = new CookProtocol(Constant.URL.LIFE_COOKBOOK, mId);
        try {
            mDatas = mProtocol.loadData(1);
            return checkState(mDatas);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_food, null);
        ViewUtils.inject(this, view);
        CookAdapter adapter = new CookAdapter(mDatas, UIUtils.getContext());
        mGvFood.setAdapter(adapter);
        mGvFood.setOnItemClickListener(adapter);
        return view;
    }

    class CookAdapter extends CustomBaseAdapter<CookInfo> {

        public CookAdapter(List<CookInfo> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected BaseHolder<CookInfo> getHolderInstance() {
            return new CookHolder();
        }

        @Override
        protected List<CookInfo> onLoadMore() throws Exception {
            return mProtocol.loadData(mDatas.size() / 20 + 1);
        }

        @Override
        protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            super.onNormalItemClick(parent, view, position, id);

            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            intent.putExtra(ContentBaseFragment.DATA_ID, mDatas.get(position).id);
            // 放入url里面的关键词，用于区分是资讯，知识、问答等……
            intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.LIFE_COOKBOOK);
            startActivity(intent);

        }
    }
}
