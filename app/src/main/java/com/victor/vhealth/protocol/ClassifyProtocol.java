package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.ClassifyInfo;
import com.victor.vhealth.domain.ClassifyInfoList;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 2016/7/6.
 */
public class ClassifyProtocol extends BaseProtocol<List<ClassifyInfo>> {

    private List<ClassifyInfo> mClassifyInfos;
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
    protected List<ClassifyInfo> parserJsonData(String result) {

        Gson gson = new Gson();
        ClassifyInfoList classify = gson.fromJson(result, ClassifyInfoList.class);
        mClassifyInfos = classify.tngou;
        return mClassifyInfos;
    }
}
