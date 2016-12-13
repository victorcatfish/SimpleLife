package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.TopInfo;
import com.victor.vhealth.domain.TopInfoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 热点问题的Protocol
 * Created by Victor on 2016/12/13.
 */
public class TopProtocol extends BaseProtocol<List<TopInfo>> {

    private String mUrlKey;
    private int mClassifyId;

    public TopProtocol(String urlKey, int classifyId) {
        mUrlKey = urlKey;
        mClassifyId = classifyId;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey + "/list";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected List<TopInfo> parserJsonData(String result) {

        Gson gson = new Gson();
        TopInfoList topInfoList = gson.fromJson(result, TopInfoList.class);
        return topInfoList.tngou;
    }
}
