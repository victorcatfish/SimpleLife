package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.CompanyInfo;

import java.util.HashMap;
import java.util.Map;

/** 药企详细页面的网络请求及数据解析类
 * Created by Victor on 2016/12/13.
 */
public class CompanyDetailProtocol extends BaseProtocol<CompanyInfo> {

    private String mUrlKey;
    private int mId;

    public CompanyDetailProtocol(String urlKey, int id) {
        mUrlKey = urlKey;
        mId = id;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey + "/show";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        HashMap<String, String> parmas = new HashMap<>();
        parmas.put("id", mId + "");
        return parmas;
    }

    @Override
    protected CompanyInfo parserJsonData(String result) {

        Gson gson = new Gson();
        CompanyInfo info = gson.fromJson(result, CompanyInfo.class);
        return info;
    }
}
