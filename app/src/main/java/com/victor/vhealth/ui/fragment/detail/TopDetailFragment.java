package com.victor.vhealth.ui.fragment.detail;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.domain.TopInfo;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.TopDetailProtocol;
import com.victor.vhealth.ui.fragment.DetailCommenFragment;
import com.victor.vhealth.util.StringUtils;
import com.victor.vhealth.util.UIUtils;
import com.victor.vhealth.util.WebViewHelper;
import com.victor.vhealth.widget.LoadingPager;

import java.io.IOException;

/**热点详情页面fragment
 * Created by Victor on 2016/7/10.
 */
public class TopDetailFragment extends DetailCommenFragment {

    private TopInfo mData;

//    @ViewInject(R.id.tv_detail_title)
//    private TextView mTvTitle;
//    @ViewInject(R.id.tv_detail_time)
//    private TextView mTvTime;
    @ViewInject(R.id.tv_commend_count)
    private TextView mTvCommendCount;
    @ViewInject(R.id.web_view)
    private WebView mWebView;

    private String mWebViewContent;

    @Override
    protected LoadingPager.LoadResult initData() {
        TopDetailProtocol protocol = new TopDetailProtocol(Constant.URL.LIFE_TOP, mId);
        try {
            mData = protocol.loadData(1);
            if (mData != null) {
                mWebViewContent = getWebViewContent(mData.message);
                // 将内容保存到本地的html文件中，方便返回时调用
                WebViewHelper.saveData2Localhtml(mWebViewContent, WebViewHelper.LOCAL_HTML_NAME);
            }
            return checkState(mData);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        } catch (HttpException e) {
            e.printStackTrace();
            return LoadingPager.LoadResult.ERROR;
        }

    }

    private String getWebViewContent(String message) {
        StringBuffer content = new StringBuffer();
        // 设置图片自适应
        content.append(WebViewHelper.IMAGE_STYLE);
        // 添加title
        content.append(String.format("<div class='title'; style=\"font-size:20px\">%s</div><p></P>", mData.title));
        // 添加时间
        String time = StringUtils.getStringTime(mData.time);
        content.append(String.format("<div class='time'; style=\"font-size:14px;color:#888888\">%s</div>", time));
        // 添加内容主体
        content.append(message);
        return content.toString();
    }

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_detail_news, null);
        ViewUtils.inject(this, view);

        WebViewHelper.settingWevView(mWebView);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.loadDataWithBaseURL("", mWebViewContent, "text/html", "UTF-8",
                WebViewHelper.getLocalHtmlDataPath(WebViewHelper.LOCAL_HTML_NAME));
        return view;
    }

    @Override
    protected WebView getWebView() {
        return mWebView;
    }

}
