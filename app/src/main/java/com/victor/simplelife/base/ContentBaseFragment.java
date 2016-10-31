package com.victor.simplelife.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.simplelife.util.UIUtils;
import com.victor.simplelife.widget.LoadingPager;

import java.util.List;
import java.util.Map;

/** 内容Fragmeng基类
 * Created by Victor on 2016/7/5.
 */
public abstract class ContentBaseFragment extends Fragment {

    public final static String DATA_ID = "data_id";

    protected AppCompatActivity mActivity;
    private LoadingPager mLoadingPager;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    public int mId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mId = arguments.getInt(DATA_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                protected View initSuccessView() {
                    return ContentBaseFragment.this.initSuccessView();
                }

                @Override
                protected LoadResult initData() {
                    return ContentBaseFragment.this.initData();
                }
            };
        }
        return mLoadingPager;
    }

    /**和LoadingPager的同名方法，最终将数据返回给LoadingPager进行处理
     * 由子类具体实现*/
    protected abstract LoadingPager.LoadResult initData();

    /**和LoadingPager的同名方法，最终将数据返回给LoadingPager进行处理
     * 由子类具体实现*/
    protected abstract View initSuccessView();


    /**根据请求得到的数据判断请求的状态
     * @param obj 请求得到的数据
     * @return 请求返回的状态
     */
    public LoadingPager.LoadResult checkState(Object obj) {
        if (obj == null) {
            return LoadingPager.LoadResult.EMPTY;
        }
        if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return LoadingPager.LoadResult.EMPTY;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return LoadingPager.LoadResult.EMPTY;
            }
        }
        return LoadingPager.LoadResult.SUCCESS;
    }

}
