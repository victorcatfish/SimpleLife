package com.victor.vhealth.domain;

import java.util.List;

/** 美图部分的javaBean
 * Created by Victor on 2016/12/9.
 */
public class PicInfo {

    public long id;
    public int  galleryclass ;//          图片分类 
    public String title     ;//          标题 
    public String img     ;//          图库封面 
    public int count     ;//          访问数 
    public int rcount     ;//           回复数 
    public int fcount     ;//          收藏数 
    public int size     ;//      图片多少张
    public long time;
    public List<DetailPic> list;

    public class DetailPic {

        public int gallery;   //: 100,
        public int id;         //: 1435,
        public String src;      //: "/ext/150728/d937d0a43006fbda2dc9615b5b538716.jpg"
    }
}
