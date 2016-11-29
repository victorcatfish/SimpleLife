package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.BodyInfoList;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 2016/11/24.
 */
public class BodyProtocol extends BaseProtocol<List<BodyInfoList.BodyClassify>> {

    private String mUrlKey;

    public BodyProtocol(String urlKey) {
        mUrlKey = urlKey;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey + "/all";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        return null;
    }

    @Override
    protected List<BodyInfoList.BodyClassify> parserJsonData(String result) {

        Gson gson = new Gson();
        BodyInfoList bodyInfoList = gson.fromJson(result, BodyInfoList.class);
        return bodyInfoList.tngou;
    }
}
