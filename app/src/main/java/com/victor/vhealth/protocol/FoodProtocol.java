package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.FoodInfo;
import com.victor.vhealth.domain.FoodInfoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 食品集合的网络请求及解析类
 * Created by Victor on 2016/12/13.
 */
public class FoodProtocol extends BaseProtocol<List<FoodInfo>> {

    private String mUrlKey;
    private int mClassifyId;

    public FoodProtocol(String urlKey, int classifyId) {
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
    protected List<FoodInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        FoodInfoList foodInfoList = gson.fromJson(result, FoodInfoList.class);
        return foodInfoList.tngou;
    }
}
