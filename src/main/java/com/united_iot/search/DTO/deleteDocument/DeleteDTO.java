package com.united_iot.search.DTO.deleteDocument;

import lombok.Data;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/28 0028
 * @time： 16:23
 * @project_name： demo
 * @Description ：
 */
@Data
public class DeleteDTO {

    private String indexName;

    private String type;

    private String id;

    private Map<String, String> fieldData;

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

    public Map<String, String> getFieldData() {
        return fieldData;
    }

    public void setFieldData(Map<String, String> fieldData) {
        this.fieldData = fieldData;
    }
}
