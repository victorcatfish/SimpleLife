package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.PharmacyInfo;
import com.victor.vhealth.domain.PharmacyInfoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**药品网络请求及数据解析类
 * Created by Victor on 2016/11/17.
 */
public class PharmacyProtocol extends BaseProtocol<List<PharmacyInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;

    public PharmacyProtocol(int classifyId, String urlPrimary) {
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
    protected List<PharmacyInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        PharmacyInfoList pharmacyInfoList = gson.fromJson(result, PharmacyInfoList.class);
        List<PharmacyInfo> pharmacyInfos = pharmacyInfoList.tngou;
        return pharmacyInfos;
    }
}
