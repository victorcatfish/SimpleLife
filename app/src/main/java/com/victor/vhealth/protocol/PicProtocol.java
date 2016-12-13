package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.PicInfo;
import com.victor.vhealth.domain.PicInfoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 美图部分的网络请求及数据解析
 * Created by Victor on 2016/12/9.
 */
public class PicProtocol extends BaseProtocol<List<PicInfo>> {

    public static final int PAGE_SIZE = 10;

    private String mUrlKey;
    private int mClassifyId;


    public PicProtocol(String urlKey, int id) {
        mUrlKey = urlKey;
        mClassifyId = id;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey;
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        params.put("rows", PAGE_SIZE + "");
        return params;
    }

    @Override
    protected List<PicInfo> parserJsonData(String result) {

        Gson gson = new Gson();
        PicInfoList picInfoList = gson.fromJson(result, PicInfoList.class);
        return picInfoList.tngou;
    }
}
