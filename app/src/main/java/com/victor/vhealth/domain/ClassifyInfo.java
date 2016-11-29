package com.victor.vhealth.domain;

/**
 * Created by Victor on 2016/11/19.
 */
public class ClassifyInfo {

    public int id; //分类id，需要查询该类下的列表就需要传入才参数
    public String name; //分类名称
    public String title; //分类的标题
    public String keywords; //分类的关键词
    public String description; //分类的描述
    public int seq;//排序 从0。。。。10开始
    public int drugclass;

    @Override
    public String toString() {
        return "ClassifyInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seq=" + seq +
                '}';
    }

}
