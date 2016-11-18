package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.domain.HospitalList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**医院门诊网络请求及数据解析类
 * Created by Victor on 2016/11/17.
 */
public class HospitalProtocol extends BaseProtocol<List<HospitalInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;

    public HospitalProtocol(int classifyId, String urlPrimary) {
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
    protected List<HospitalInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        HospitalList hospitalList = gson.fromJson(result, HospitalList.class);
        List<HospitalInfo> hospitalInfos = hospitalList.tngou;
        return hospitalInfos;
    }
}
