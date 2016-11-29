package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.KeyWordSearchBaseProtocol;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.domain.DiseaseInfoList;

import java.util.List;

/**
 * Created by Victor on 2016/11/28.
 */
public class DiseaseKeywordSearchProtocol extends KeyWordSearchBaseProtocol<List<DiseaseInfo>> {

    public DiseaseKeywordSearchProtocol(String classifyName, String keyword) {
        super(classifyName, keyword);
    }

    @Override
    protected List<DiseaseInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        DiseaseInfoList diseaseInfoList = gson.fromJson(result, DiseaseInfoList.class);
        return diseaseInfoList.tngou;
    }
}
