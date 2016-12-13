package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.PicInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 2016/12/9.
 */
public class DetailPicProtocol extends BaseProtocol<PicInfo> {

    private String mUrlKey;
    private int mClassifyId;

    public DetailPicProtocol(String urlKey, int classifyId) {
        mUrlKey = urlKey;
        mClassifyId = classifyId;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey;
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected PicInfo parserJsonData(String result) {
        Gson gson = new Gson();
        PicInfo picInfo = gson.fromJson(result, PicInfo.class);
        return picInfo;
    }
}
