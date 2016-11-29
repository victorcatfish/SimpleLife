package com.victor.vhealth.protocol;

import com.google.gson.Gson;
import com.victor.vhealth.base.BaseProtocol;
import com.victor.vhealth.domain.DepartmentList;

import java.util.List;
import java.util.Map;

/** 科室分类网络请求和解析类
 * Created by Victor on 2016/11/24.
 */
public class DepartmentPrototol extends BaseProtocol<List<DepartmentList.DepartmentClassify>> {

    private String mUrlKey;

    public DepartmentPrototol(String urlKey) {
        mUrlKey = urlKey;
    }

    @Override
    protected String getUrlKey() {
        return mUrlKey + "/all";
    }

    @Override
    protected Map<String, String> getExtraParmas() {
        return null;
    }

    @Override
    protected List<DepartmentList.DepartmentClassify> parserJsonData(String result) {

        Gson gson = new Gson();
        DepartmentList departmentClassifies = gson.fromJson(result, DepartmentList.class);
        return departmentClassifies.tngou;
    }
}
