package com.victor.simplelife.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.simplelife.R;
import com.victor.simplelife.base.ContentBaseFragment;
import com.victor.simplelife.domain.HealthClassify;
import com.victor.simplelife.factory.CustomFragmentFactory;
import com.victor.simplelife.factory.ThreadPoolFactory;
import com.victor.simplelife.protocol.ClassifyProtocol;
import com.victor.simplelife.util.UIUtils;

import java.io.IOException;
import java.util.List;

/** 内容分类Fragment
 * Created by Victor on 2016/7/1.
 */
public class ClassifyFragment extends Fragment {

    public static final String CHANNEL_TAG = "channel_tag";
    public static final String URL_KEY = "url_key";

    @ViewInject(R.id.vp_content_list)
    ViewPager mVpContent;

    @ViewInject(R.id.pts_content_indicator)
    PagerSlidingTabStrip mPagerIndicator;

    private List<HealthClassify.ClassifyInfo> mClassifyInfos;
    private ContentAdapter mAdapter;
    private String mUrlKey;
    private String mChannelTag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrlKey = arguments.getString(URL_KEY);
            mChannelTag = arguments.getString(CHANNEL_TAG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_base_pager, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected void initData() {
        final ClassifyProtocol protocol = new ClassifyProtocol(mUrlKey);
        ThreadPoolFactory.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mClassifyInfos = protocol.loadData(0);
                    UIUtils.runningOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshView();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshView() {
        if (mAdapter == null) {
            mAdapter = new ContentAdapter(getChildFragmentManager());
        }
        mVpContent.setAdapter(mAdapter);
        // 将页面的Indicator和Pager关联起来
        mPagerIndicator.setViewPager(mVpContent);
        mPagerIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ContentBaseFragment contentFragment = CustomFragmentFactory.
                        createContentFragment(mUrlKey, mClassifyInfos.get(position).id);
                contentFragment.getLoadingPager().loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class ContentAdapter extends FragmentStatePagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentBaseFragment contentFragment = CustomFragmentFactory.
                    createContentFragment(mUrlKey, mClassifyInfos.get(position).id);
            return contentFragment;
        }

        @Override
        public int getCount() {
            if (mClassifyInfos != null) {
                return mClassifyInfos.size();
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mClassifyInfos != null) {
                String briefName = getClassifyBriefName(mClassifyInfos.get(position).name);
                return briefName;
            }
            return "";
        }

    }

    private String getClassifyBriefName(String name) {
        String shortName = name.substring(0, 2);
        return shortName;
    }


}
