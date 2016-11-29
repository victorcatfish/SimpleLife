package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.DrugInfo;

import java.util.HashMap;
import java.util.Map;

/** 药物详情信息网络请求及数据解析
 * Created by Victor on 2016/11/21.
 */
public class DrugDetailProtocol extends BaseProtocol<DrugInfo> {

    private int mDrugId;
    private String mUrlPrimary;

    public DrugDetailProtocol(int drugId, String urlPrimary) {
        mDrugId = drugId;
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/show";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        Map<String, String> params = new HashMap<>();
        params.put("id", mDrugId + "");
        return params;
    }

    @Override
    protected DrugInfo parserJsonData(String result) {
        Gson gson = new Gson();
        DrugInfo drugInfo = gson.fromJson(result, DrugInfo.class);
        return drugInfo;
    }
}
