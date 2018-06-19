package com.united_iot.search.DTO.NewDTO;

import lombok.Data;

import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 15:41
 * @project_name： search
 * @Description ：
 */
@Data
public class SearchTableDTO {

    private String tableName;

    private List<FieldNewDTO> fields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<FieldNewDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldNewDTO> fields) {
        this.fields = fields;
    }
}
