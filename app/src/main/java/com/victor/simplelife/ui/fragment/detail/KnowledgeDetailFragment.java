package com.victor.simplelife.ui.fragment.detail;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.simplelife.R;
import com.victor.simplelife.domain.HealthInfo;
import com.victor.simplelife.global.Constant;
import com.victor.simplelife.protocol.DetailProtocol;
import com.victor.simplelife.ui.fragment.DetailCommenFragment;
import com.victor.simplelife.util.StringUtils;
import com.victor.simplelife.util.UIUtils;
import com.victor.simplelife.util.WebViewHelper;
import com.victor.simplelife.widget.LoadingPager;

import java.io.IOException;

/**资讯详情页面fragment
 * Created by Victor on 2016/7/10.
 */
public class KnowledgeDetailFragment extends DetailCommenFragment {


    private HealthInfo mData;

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
        DetailProtocol protocol = new DetailProtocol(Constant.URL.HEALTH_KNOWLEDGE, mId);
        try {
            mData = protocol.loadData(1);
            if (mData != null) {
                mWebViewContent = getWebViewContent(mData.message);
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
