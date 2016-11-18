package com.victor.vhealth.domain;

/** 疾病信息javabean
 * Created by Victor on 2016/11/17.
 */
public class DiseaseInfo extends MedicineInfo{

    public String department;//疾病科室
    public String place;//疾病部位
    public String keywords;
    public String description;
    public String symptomtext;//病状描述
    public String symptom;//相关症状
    public String drug;//相关药品
    public String drugtext;//用药说明
    public String food;//相关食品
    public String foodtext;//健康保健
    public String causetext;//病因
    public String checks;//检测项目
    public String checktext;//检测说明
    public String disease;//并发疾病
    public String diseasetext;//并发症状说明
    public String caretext;//预防护理
    public long time;

}
