package com.victor.vhealth.domain;

/**
 * Created by Victor on 2016/7/5.
 */
public class HealthInfo {

    public String title;//资讯标题
    public int infoclass;//分类
    public String img;//图片
    public String description;//描述
    public String keywords;//关键字
    public String message;//资讯内容
    public int count ;//访问次数
    public int fcount;//收藏数
    public int rcount;//评论读数
    public long time;// 时间
    public int id;
    public String url;

    @Override
    public String toString() {
        return "HealthInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
