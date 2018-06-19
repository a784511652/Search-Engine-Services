package com.united_iot.search.DTO.addDocument;

import lombok.Data;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/27 0027
 * @time： 19:01
 * @project_name： demo
 * @Description ：
 */
@Data
public class AddDTO {

    //	索引名
    private String indexName;

    //定义类型
    private String type;

    //文档id
    private String id;

    //字段数据
    private Map fieldData;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getFieldData() {
        return fieldData;
    }

    public void setFieldData(Map fieldData) {
        this.fieldData = fieldData;
    }
}
