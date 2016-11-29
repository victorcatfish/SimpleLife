package com.victor.vhealth.domain;

import java.util.List;

/** 身体部位信息的列表javabean
 * Created by Victor on 2016/11/24.
 */
public class BodyInfoList {

    public boolean status;

    public List<BodyClassify> tngou;


    public class BodyClassify {
       public String description;   //: "头部",
       public int id;           //: 1,
       public String keywords;         //: "头部",
       public String name;          //: "头部",
       public String place;        //: 0,
       public List<BodyInfo> places;
    }
}
