package com.victor.vhealth.domain;

import java.util.List;

/**药品信息javaBean
 * Created by Victor on 2016/11/17.
 */
public class DrugInfo extends MedicineInfo{

    public String keywords;
    public String description;
    public float price;//价格
    public String tag; //标签
    public String type 	;//类型
    public List<Barcode> codes; // 条形码
    public List<NMPN> numbers; // 国药准字


    public class Barcode {
        public String code;     //: "6905070601772", 条形码
        public int drug;       //: 10,药品ID
        public String factory;       //: "大同市利群药业有限责任公司",
        public int id;       //: 97
    }

    public class NMPN {
        public int drug;    //: 10,
        public String factory;    //: "吉林敖东集团大连药业股份有限公司",
        public int id;     //: 215,
        public String number;     //: "H21024049"
    }

}
