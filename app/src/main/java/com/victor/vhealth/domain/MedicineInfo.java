package com.victor.vhealth.domain;

/** 医疗频道javabean的共同父类
 * Created by Victor on 2016/11/18.
 */
public class MedicineInfo {

    public long id;
    public String name;//名称
    public String img;//图片
    public String message;//内容、简介等
    public int count;//访问次数
    public int fcount;//收藏数
    public int rcount;//评论读数
}
