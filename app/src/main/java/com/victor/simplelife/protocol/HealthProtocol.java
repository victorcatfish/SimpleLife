package com.victor.simplelife.protocol;

import com.google.gson.Gson;
import com.victor.simplelife.base.BaseProtocol;
import com.victor.simplelife.domain.HealthNewsList;
import com.victor.simplelife.domain.HealthInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 2016/7/6.
 */
public class HealthProtocol extends BaseProtocol<List<HealthInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;

    public HealthProtocol(String urlPrimary, int classifyId) {
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
    protected List<HealthInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        HealthNewsList newsList = gson.fromJson(result, HealthNewsList.class);
        List<HealthInfo> healthInfos = newsList.tngou;
        return healthInfos;
    }

}
