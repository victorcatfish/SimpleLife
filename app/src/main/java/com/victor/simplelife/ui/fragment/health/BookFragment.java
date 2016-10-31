package com.victor.simplelife.ui.fragment.health;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.simplelife.R;
import com.victor.simplelife.base.BaseHolder;
import com.victor.simplelife.base.ContentBaseFragment;
import com.victor.simplelife.base.CustomBaseAdapter;
import com.victor.simplelife.domain.BookInfo;
import com.victor.simplelife.global.Constant;
import com.victor.simplelife.holder.BookHolder;
import com.victor.simplelife.protocol.BookProtocol;
import com.victor.simplelife.ui.activity.DetailActivity;
import com.victor.simplelife.util.UIUtils;
import com.victor.simplelife.widget.LoadingPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 2016/7/5.
 */
public class BookFragment extends ContentBaseFragment {


    private List<BookInfo> mDatas;
    @ViewInject(R.id.lv_success)
    private ListView mListView;

    @ViewInject(R.id.srl_refresh)
    private SwipeRefreshLayout mSwipeRefresh;
    private BookProtocol mProtocol;
    private BookAdapter mAdapter;

    @Override
    protected LoadingPager.LoadResult initData() {
        mProtocol = new BookProtocol(Constant.URL.HEALTH_BOOK, mId);
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
        mAdapter = new BookAdapter(mDatas, UIUtils.getContext());
        ViewUtils.inject(this, successView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mAdapter);
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return successView;
    }

    /**下拉刷新具体操作*/
    private void refresh() {
        try {
            // 由于服务器没有下拉刷新的接口, 这里就重新去请求一次服务器的第一页数据，
            // 再判断是否有新的数据被
            final List<BookInfo> newDatas = mProtocol.loadData(1);
            if (newDatas != null && newDatas.size() > 0) {
                // 获取到数据之后，根据新闻的id来判断和剔除重复数据
                List<BookInfo> repeatDatas = new ArrayList<BookInfo>();
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

    class BookAdapter extends CustomBaseAdapter<BookInfo> {

        public BookAdapter(List<BookInfo> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected BaseHolder<BookInfo> getHolderInstance() {
            return new BookHolder();
        }

        @Override
        protected void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
            intent.putExtra(ContentBaseFragment.DATA_ID, mDatas.get(position).id);
            // 放入url里面的关键词，用于区分是资讯，知识、问答等……
            intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.HEALTH_BOOK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);
        }
    }
}
