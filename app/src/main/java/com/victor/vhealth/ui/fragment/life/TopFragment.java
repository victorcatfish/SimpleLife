package com.victor.vhealth.ui.fragment.life;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.BaseHolder;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.base.CustomBaseAdapter;
import com.victor.vhealth.domain.TopInfo;
import com.victor.vhealth.domain.TopPicInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.holder.TopHolder;
import com.victor.vhealth.holder.TopPicHolder;
import com.victor.vhealth.protocol.TopProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 2016/7/7.
 */
public class TopFragment extends ContentBaseFragment {


    private List<TopInfo> mDatas;
    private List<TopPicInfo> mTopPicDatas;
    private TopProtocol mProtocol;
    private TopAdapter mAdapter;

    @ViewInject(R.id.lv_success)
    private ListView mListView;

    @ViewInject(R.id.srl_refresh)
    private SwipeRefreshLayout mSwipeRefresh;
    private TopPicHolder mTopPicHolder;

    @Override
    protected LoadingPager.LoadResult initData() {

        mProtocol = new TopProtocol(Constant.URL.LIFE_TOP, mId);
        try {
            mDatas = mProtocol.loadData(1);
            mTopPicDatas = new ArrayList<>();
            if(mDatas != null) {
                for (int i = 0; i < mDatas.size(); i++) {
                    if (i % 4 == 0) {
                        TopPicInfo topPicInfo = new TopPicInfo();
                        topPicInfo.title = mDatas.get(i).title;
                        topPicInfo.imgUrl = mDatas.get(i).img;
                        topPicInfo.id = (int) mDatas.get(i).id;
                        mTopPicDatas.add(topPicInfo);
                    }
                }
            }
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
        View successView = View.inflate(UIUtils.getContext(), R.layout.layout_success_health, null);
        ViewUtils.inject(this, successView);
        mAdapter = new TopAdapter(mDatas, UIUtils.getContext());

        // 初始化HeaderView
        mTopPicHolder = new TopPicHolder();
        // 手动触发加载和刷新数据
        mTopPicHolder.setDataAndRefreshView(mTopPicDatas);
        View headerView = mTopPicHolder.mHolderView;
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mListView.addHeaderView(headerView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mAdapter);
        return successView;
    }

    /**下拉刷新具体操作*/
    private void refresh() {
        try {
            // 由于服务器没有下拉刷新的接口, 这里就重新去请求一次服务器的第一页数据，
            // 再判断是否有新的数据被
            final List<TopInfo> newDatas = mProtocol.loadData(1);
            if (newDatas != null && newDatas.size() > 0) {
                // 获取到数据之后，根据新闻的id来判断和剔除重复数据
                List<TopInfo> repeatDatas = new ArrayList<>();
                for (int i = 0; i < newDatas.size(); i++) {
                    int id = (int)newDatas.get(i).id;
                    for (int j = 0; j < mDatas.size(); j++) {
                        int oldId = (int)mDatas.get(j).id;
                        if (id == oldId) {
                            repeatDatas.add(newDatas.get(i));
                            break;
                        }
                    }
                }
                newDatas.removeAll(repeatDatas);
                UIUtils.postTaskOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        // 剔除重复数据之后 如果确实有新的数据，
                        // 将新数据加入到集合并刷新页面
                        if (newDatas.size() > 0) {
                            mDatas.addAll(0, newDatas);
                            mAdapter.notifyDataSetChanged();
                        }
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            mSwipeRefresh.setRefreshing(false);
        } catch (HttpException e) {
            e.printStackTrace();
            mSwipeRefresh.setRefreshing(false);
        }

    }

    class TopAdapter extends CustomBaseAdapter<TopInfo> {


        public TopAdapter(List<TopInfo> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected BaseHolder<TopInfo> getHolderInstance() {
            return new TopHolder();
        }

        @Override
        protected List<TopInfo> onLoadMore() throws Exception {
            return mProtocol.loadData(mDatas.size()/20 + 1);
        }

        @Override
        protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 开启资讯详情页面
            // 开启资讯详情页面
            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            intent.putExtra(ContentBaseFragment.DATA_ID, (int)mDatas.get(position).id);
            // 放入url里面的关键词，用于区分是资讯，知识、问答等……
            intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.LIFE_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTopPicHolder != null) {
            mTopPicHolder.stopScroll();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mTopPicHolder != null) {
                mTopPicHolder.startScroll();
            }
        }
    }
}
