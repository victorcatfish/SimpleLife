package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.DiseaseInfo;

import java.util.HashMap;
import java.util.Map;

/** 疾病信息详情界面的数据请求和解析
 * Created by Victor on 2016/11/25.
 */
public class DiseaseDetailProtocol extends BaseProtocol<DiseaseInfo> {

    private int mDrugId;
    private String mUrlPrimary;

    public DiseaseDetailProtocol(int drugId, String urlPrimary) {
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
    protected DiseaseInfo parserJsonData(String result) {

        Gson gson = new Gson();
        DiseaseInfo diseaseInfo = gson.fromJson(result, DiseaseInfo.class);
        return diseaseInfo;
    }
}
