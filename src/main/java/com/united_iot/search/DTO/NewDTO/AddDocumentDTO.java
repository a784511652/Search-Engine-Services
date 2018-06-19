package com.united_iot.search.DTO.NewDTO;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2018/1/17 0017
 * @time： 10:57
 * @project_name： search
 * @Description ：
 */

public class AddDocumentDTO {
    //	索引名
    private String dbName;

    //定义类型
    private String tbName;

    //文档id
    private String id;

    //字段数据
    private Map docData;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getDocData() {
        return docData;
    }

    public void setDocData(Map docData) {
        this.docData = docData;
    }
}
