package com.victor.vhealth.domain;

import java.util.List;

/**
 * Created by Victor on 2016/11/24.
 */
public class DepartmentList {

    public boolean status;
    public List<DepartmentClassify> tngou;

    public class DepartmentClassify {

        public String department;   //: 0,
        public String description;     //: "",
        public int id;    //: 1,
        public String keywords;    //: "",
        public String name;      //: "内科",
        public String seq;          //: 0,
        public String title;             //: ""
        public List<DepartmentInfo> departments;

    }

}
