package com.united_iot.search.DTO.NewDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 8:41
 * @project_name： search
 * @Description ：
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldNewDTO {
    //字段名
    private String fieldName;

    //字段类型
    private String fieldType;

    //分词器
    private String analyzer;


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

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }
}
