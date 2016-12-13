package com.victor.vhealth.ui.fragment.pic;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.adapter.PicAdapter;
import com.victor.vhealth.base.ContentBaseFragment;
import com.victor.vhealth.domain.PicInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.PicProtocol;
import com.victor.vhealth.ui.activity.PicDetailActivity;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;
import java.util.List;

/** 美图部分的Fragment
 * Created by Victor on 2016/11/17.
 */
public class PicFragment extends ContentBaseFragment {




    @ViewInject(R.id.rv_pic)
    private RecyclerView mRvPic;
    private List<PicInfo> mPicInfos;
    private PicProtocol mPicProtocol;

    @Override
    protected LoadingPager.LoadResult initData() {

        mPicProtocol = new PicProtocol(Constant.URL.PIC_LIST, mId);
        try {
            mPicInfos = mPicProtocol.loadData(1);
            return checkState(mPicInfos);
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
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_pic, null);
        ViewUtils.inject(this, view);

        // 配置layoutManager
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRvPic.setLayoutManager(layoutManager);
        PicAdapter adapter = new PicAdapter(mPicInfos, mPicProtocol);
        adapter.setOnItemClickListener(new PicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(UIUtils.getContext(), PicDetailActivity.class);
                intent.putExtra(PicDetailActivity.PIC_ID, (int)mPicInfos.get(position).id);
                intent.putExtra(PicDetailActivity.PIC_TITLE, mPicInfos.get(position).title);
                intent.putExtra(PicDetailActivity.PIC_SIZE, mPicInfos.get(position).size);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mRvPic.setAdapter(adapter);

        return view;
    }
}
