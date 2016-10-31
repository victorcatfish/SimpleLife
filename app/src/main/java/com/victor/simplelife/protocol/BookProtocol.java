package com.victor.simplelife.protocol;

import com.google.gson.Gson;
import com.victor.simplelife.base.BaseProtocol;
import com.victor.simplelife.domain.BookInfo;
import com.victor.simplelife.domain.BookList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 2016/7/6.
 */
public class BookProtocol extends BaseProtocol<List<BookInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;
    public BookProtocol(String urlPrimary, int classifyId) {
        mClassifyId = classifyId;
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/list";
    }

    @Override
    protected List<BookInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        BookList bookList = gson.fromJson(result, BookList.class);
        List<BookInfo> healthInfos = bookList.list;
        return healthInfos;
    }

}
