package com.victor.vhealth.ui.fragment.health;

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
import com.victor.vhealth.adapter.HealthAdapter;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.HealthInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.HealthProtocol;
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**问答页面fragment
 * Created by Victor on 2016/7/5.
 */
public class AskFragment extends ContentBaseFragment {


    private List<HealthInfo> mDatas;
    private HealthProtocol mProtocol;

    @ViewInject(R.id.srl_refresh)
    private SwipeRefreshLayout mSwipeRefresh;

    @ViewInject(R.id.lv_success)
    private ListView mListView;

    private HealthAdapter mAdapter;

    @Override
    protected LoadingPager.LoadResult initData() {
        mProtocol = new HealthProtocol(Constant.URL.HEALTH_ASK, mId);
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
        View successView = View.inflate(UIUtils.getContext(), R.layout.layout_success_health, null);
        ViewUtils.inject(this, successView);
        mAdapter = new AskAdapter(mDatas, UIUtils.getContext());

        // 初始化HeaderView
        // 手动触发加载和刷新数据
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mAdapter);
        return successView;
    }

    class AskAdapter extends HealthAdapter {

        public AskAdapter(List<HealthInfo> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected List<HealthInfo> onLoadMore() throws Exception {
            return mProtocol.loadData(mDatas.size()/20 + 1);
        }

        @Override
        protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 开启资讯详情页面
            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            intent.putExtra(ContentBaseFragment.DATA_ID, mDatas.get(position).id);
            // 放入url里面的关键词，用于区分是资讯，知识、问答等……
            intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.HEALTH_ASK);
            startActivity(intent);
        }
    }

    /**下拉刷新具体操作*/
    private void refresh() {
        try {
            // 由于服务器没有下拉刷新的接口, 这里就重新去请求一次服务器的第一页数据，
            // 再判断是否有新的数据被
            final List<HealthInfo> newDatas = mProtocol.loadData(1);
            if (newDatas != null && newDatas.size() > 0) {
                // 获取到数据之后，根据新闻的id来判断和剔除重复数据
                List<HealthInfo> repeatDatas = new ArrayList<HealthInfo>();
                for (int i = 0; i < newDatas.size(); i++) {
                    int id = newDatas.get(i).id;
                    for (int j = 0; j < mDatas.size(); j++) {
                        int oldId = mDatas.get(j).id;
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
}
