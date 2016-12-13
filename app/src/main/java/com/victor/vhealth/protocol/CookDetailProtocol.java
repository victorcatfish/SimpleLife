package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.CookInfo;

import java.util.HashMap;
import java.util.Map;

/** 菜谱详细页面的网络请求及数据解析类
 * Created by Victor on 2016/12/13.
 */
public class CookDetailProtocol extends BaseProtocol<CookInfo> {

    private String mUrlKey;
    private int mId;

    public CookDetailProtocol(String urlKey, int id) {
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
    protected CookInfo parserJsonData(String result) {

        Gson gson = new Gson();
        CookInfo foodInfo = gson.fromJson(result, CookInfo.class);
        return foodInfo;
    }
}
