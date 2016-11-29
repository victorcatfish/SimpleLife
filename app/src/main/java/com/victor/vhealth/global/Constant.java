package com.victor.vhealth.global;

import com.victor.vhealth.util.LogUtils;

/** 全局的静态常量
 * Created by Victor on 2016/7/5.
 */
public class Constant {

    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final String LIFE_CHANNEL = "life_channel";
    public static final String HEALTH_CHANNEL = "health_channel";
    public static final int TOP_PIC_PAGER_SIZE = 500000;
    public static final long CACHE_LIMITED_TIME = 30 * 60 * 1000;

    public static class URL {
        public static final String BASE = "http://www.tngou.net/api";
        public static final String IMG_BASE = "http://tnfs.tngou.net/image";

        public static final String HEALTH_NEWS = "/info";
        public static final String HEALTH_KNOWLEDGE = "/lore";
        public static final String HEALTH_ASK = "/ask";
        public static final String HEALTH_BOOK = "/book";

        public static final String LIFE_TOP = "/top";
        public static final String LIFE_FOOD = "/food";
        public static final String LIFE_COOKBOOK = "/cook";

        public static final String MEDICINE_DRUG = "/drug";
        public static final String MEDICINE_DISEASE = "/disease";
        public static final String MEDICINE_DISEASE_LIST = MEDICINE_DISEASE + "/list";
        public static final String MEDICINE_HOSPTIAL = "/hospital";
        public static final String MEDICINE_PHARMACY = "/store";
        public static final String MEDICINE_COMPANY= "/factory";
        public static final String MEDICINE_DEPARTMENT = "/department";
        public static final String MEDICINE_BODY = "/place";

        public static final String SEARCH = "/search";
    }


    public static class SearchKeyword {
        public static final String DRUG = "drug";
        public static final String DISEASE = "disease";
    }
}
