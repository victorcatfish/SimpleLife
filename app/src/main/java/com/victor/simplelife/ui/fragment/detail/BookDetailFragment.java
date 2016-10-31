package com.victor.simplelife.ui.fragment.detail;

import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.simplelife.R;
import com.victor.simplelife.domain.BookInfo;
import com.victor.simplelife.global.Constant;
import com.victor.simplelife.protocol.BookDetailProtocol;
import com.victor.simplelife.ui.fragment.DetailCommenFragment;
import com.victor.simplelife.util.BitmapHelper;
import com.victor.simplelife.util.StringUtils;
import com.victor.simplelife.util.UIUtils;
import com.victor.simplelife.widget.LoadingPager;

import java.io.IOException;

/**图书详情页面fragment
 * Created by Victor on 2016/7/10.
 */
public class BookDetailFragment extends DetailCommenFragment {


//    @ViewInject(R.id.tv_detail_title)
//    private TextView mTvTitle;
//    @ViewInject(R.id.tv_detail_time)
//    private TextView mTvTime;

    @ViewInject(R.id.tv_detail_book_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_detail_book_author)
    private TextView mTvAuthor;
    @ViewInject(R.id.tv_detail_book_count)
    private TextView mTvReadCount;
    @ViewInject(R.id.tv_detail_commend)
    private TextView mTvCommend;
    @ViewInject(R.id.tv_book_class)
    private TextView mTvClass;
    @ViewInject(R.id.tv_detail_des)
    private TextView mTvDes;
    @ViewInject(R.id.iv_detail_book_cover)
    private ImageView mIvCover;
    @ViewInject(R.id.tv_book_detail_time)
    private TextView mTvTime;

    @ViewInject(R.id.btn_detail_read)
    private Button mBtnRead;
    @ViewInject(R.id.btn_detal_favo)
    private Button mBtnFavo;

    private String mWebViewContent;
    private BookInfo mData;

    @Override
    protected LoadingPager.LoadResult initData() {
        BookDetailProtocol protocol = new BookDetailProtocol(Constant.URL.HEALTH_BOOK, mId);
        try {
            mData = protocol.loadData(1);
            return checkState(mData);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }
    }

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_book_detail, null);
        ViewUtils.inject(this, view);

        mBtnRead.setOnClickListener(this);
        mBtnFavo.setOnClickListener(this);

        mTvName.setText(mData.name);
        mTvAuthor.setText("作者: " + mData.author);
        mTvReadCount.setText("阅读量: " + mData.count);
        String[] bookClassifies = UIUtils.getStringArray(R.array.book_classify);
        mTvClass.setText("分类: " + bookClassifies[mData.bookclass]);
        mTvTime.setText("上架时间: " + StringUtils.getStringTime(mData.time));
        mTvDes.setText(mData.summary);
        if (mData.rcount == 0) {
            mTvCommend.setGravity(Gravity.CENTER);
            mTvCommend.setText("暂无评论\n块来抢占沙发吧!");
        }

        BitmapHelper.display(mIvCover, Constant.URL.IMG_BASE + mData.img);
        return view;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_detail_read:
                UIUtils.showShortToast(UIUtils.getContext(), "开始阅读");
                break;
            case R.id.btn_detal_favo:
                UIUtils.showLongToast(UIUtils.getContext(), "加入书架");
                break;
        }
    }

    @Override
    protected WebView getWebView() {
        return null;
    }

}
