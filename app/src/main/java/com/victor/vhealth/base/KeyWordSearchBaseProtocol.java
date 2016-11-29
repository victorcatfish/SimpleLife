package com.victor.vhealth.base;

import com.victor.vhealth.global.Constant;

import java.util.HashMap;
import java.util.Map;

/** 通过关键字搜索fragment基类
 * Created by Victor on 2016/11/28.
 */
public abstract class KeyWordSearchBaseProtocol<T> extends BaseProtocol<T> {

    private String mClassifyName;
    private String mKeyword;

    public KeyWordSearchBaseProtocol(String classifyName, String keyword) {
        mClassifyName = classifyName;
        mKeyword = keyword;
    }

    @Override
    protected String getUrlKey() {
        return Constant.URL.SEARCH;
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("name", mClassifyName);
        parmas.put("keyword", mKeyword);
        return parmas;
    }
}
