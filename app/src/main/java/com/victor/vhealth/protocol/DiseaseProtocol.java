package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.domain.DiseaseInfoList;

import java.util.List;
import java.util.Map;

/**疾病信息请求及数据解析类
 * Created by Victor on 2016/11/17.
 */
public class DiseaseProtocol extends BaseProtocol<List<DiseaseInfo>> {

    private String mUrlPrimary;

    public DiseaseProtocol(String urlPrimary) {
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/list";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        return null;
    }

    @Override
    protected List<DiseaseInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        DiseaseInfoList diseaseInfoList = gson.fromJson(result, DiseaseInfoList.class);
        List<DiseaseInfo> diseaseInfos = diseaseInfoList.list;
        return diseaseInfos;
    }
}
