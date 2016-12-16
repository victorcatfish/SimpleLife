package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.CompanyInfo;
import com.victor.vhealth.domain.CompanyInfoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**药品网络请求及数据解析类
 * Created by Victor on 2016/11/17.
 */
public class CompanyProtocol extends BaseProtocol<List<CompanyInfo>> {

    private int mClassifyId;
    private String mUrlPrimary;

    public CompanyProtocol(int classifyId, String urlPrimary) {
        mClassifyId = classifyId;
        mUrlPrimary = urlPrimary;
    }

    @Override
    protected String getUrlKey() {
        return mUrlPrimary + "/list";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        if (mClassifyId == -1) {
            return null;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", mClassifyId + "");
        return params;
    }

    @Override
    protected List<CompanyInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        CompanyInfoList companyInfoList = gson.fromJson(result, CompanyInfoList.class);
        List<CompanyInfo> companyInfos = companyInfoList.tngou;
        return companyInfos;
    }
}
