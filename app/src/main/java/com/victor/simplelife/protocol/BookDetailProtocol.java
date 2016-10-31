package com.victor.simplelife.protocol;

import com.google.gson.Gson;
import com.victor.simplelife.base.BaseProtocol;
import com.victor.simplelife.domain.BookInfo;

import java.util.HashMap;
import java.util.Map;

/** 详情页面的网络请求和数据解析
 * Created by Victor on 2016/7/10.
 */
public class BookDetailProtocol extends BaseProtocol<BookInfo> {

    private int mClassifyId;
    private String mUrlPrimary;

    public BookDetailProtocol(String urlPrimary, int classifyId) {
        mClassifyId = classifyId;
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/show";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected BookInfo parserJsonData(String result) {
        Gson gson = new Gson();
        BookInfo bookInfo = gson.fromJson(result, BookInfo.class);
        return bookInfo;
    }
}
