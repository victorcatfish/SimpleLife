package com.victor.simplelife.protocol;

import com.google.gson.Gson;
import com.victor.simplelife.base.BaseProtocol;
import com.victor.simplelife.domain.HealthClassify;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 2016/7/6.
 */
public class ClassifyProtocol extends BaseProtocol<List<HealthClassify.ClassifyInfo>> {

    private List<HealthClassify.ClassifyInfo> mClassifyInfos;
    private final String mUrlKey;

    public ClassifyProtocol(String urlKey) {
        mUrlKey = urlKey;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey + "/classify";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        return null;
    }

    @Override
    protected List<HealthClassify.ClassifyInfo> parserJsonData(String result) {

        Gson gson = new Gson();
        HealthClassify classify = gson.fromJson(result, HealthClassify.class);
        mClassifyInfos = classify.tngou;
        return mClassifyInfos;
    }
}
