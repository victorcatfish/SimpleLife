package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.FoodInfo;

import java.util.HashMap;
import java.util.Map;

/** 食品详细页面的网络请求及数据解析类
 * Created by Victor on 2016/12/13.
 */
public class FoodDetailProtocol extends BaseProtocol<FoodInfo> {

    private String mUrlKey;
    private int mId;

    public FoodDetailProtocol(String urlKey, int id) {
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
    protected FoodInfo parserJsonData(String result) {

        Gson gson = new Gson();
        FoodInfo foodInfo = gson.fromJson(result, FoodInfo.class);
        return foodInfo;
    }
}
