package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.KeyWordSearchBaseProtocol;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.domain.DrugList;

import java.util.List;

/** 药品关键字搜索的网络协议类
 * Created by Victor on 2016/11/28.
 */
public class DrugKeywordSearchProtocol extends KeyWordSearchBaseProtocol<List<DrugInfo>> {



    public DrugKeywordSearchProtocol(String classifyName, String keyword) {
        super(classifyName, keyword);
    }

    @Override
    protected List<DrugInfo> parserJsonData(String result) {
        Gson gson = new Gson();
        DrugList drugList = gson.fromJson(result, DrugList.class);
        return drugList.tngou;
    }
}
