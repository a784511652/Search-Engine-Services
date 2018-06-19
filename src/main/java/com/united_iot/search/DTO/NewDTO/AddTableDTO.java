package com.united_iot.search.DTO.NewDTO;

import com.united_iot.search.DTO.FieldDTO;

import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 8:39
 * @project_name： search
 * @Description ：
 */
public class AddTableDTO {

    private String dbName;

    private String tableName;


    //字段集合
    private List<FieldNewDTO> fieldInfo;



    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<FieldNewDTO> getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(List<FieldNewDTO> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
}
