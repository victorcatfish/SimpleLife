package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.domain.DrugList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**药品网络请求及数据解析类
 * Created by Victor on 2016/11/17.
 */
public class DrugProtocol extends BaseProtocol<List<DrugInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;

    public DrugProtocol(int classifyId, String urlPrimary) {
        mClassifyId = classifyId;
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/list";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected List<DrugInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        DrugList drugList = gson.fromJson(result, DrugList.class);
        List<DrugInfo> drugInfos = drugList.tngou;
        return drugInfos;
    }
}
