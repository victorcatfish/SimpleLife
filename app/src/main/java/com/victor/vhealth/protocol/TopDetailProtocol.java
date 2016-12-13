package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.TopInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 2016/12/13.
 */
public class TopDetailProtocol extends BaseProtocol<TopInfo> {

    private int mClassifyId;
    private String mUrlPrimary;

    public TopDetailProtocol(String urlPrimary, int classifyId) {
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
    protected TopInfo parserJsonData(String result) {
        Gson gson = new Gson();
        TopInfo topInfo = gson.fromJson(result, TopInfo.class);
        return topInfo;
    }
}
