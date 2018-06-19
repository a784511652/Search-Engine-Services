package com.united_iot.search.DTO;

import lombok.Data;

/**
 * @auther jiahaowei
 * @date： 2017/11/27 0027
 * @time： 11:19
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Data
public class FieldDTO {

    //字段名
    private String fieldName;

    //字段参照名称
   // private String referenceFieldName;

    //字段类型
    private String fieldType;

    //分词器
    private String analysis="";

    //是否存储
    //private Boolean stored;

    //是否索引
    //private Boolean indexed;

    //权重值
    private Double weight;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
