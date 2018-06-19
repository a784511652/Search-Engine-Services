package com.united_iot.search.DTO.NewDTO;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2018/1/17 0017
 * @time： 15:37
 * @project_name： search
 * @Description ：
 */
public class SearchByKeywordDTO {

    private String dbName;

    private String tbName;

    private String keyword;

    private Integer page;

    private Integer pageSize;

    private Map<String,Boolean> fieldHighlight;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Boolean> getFieldHighlight() {
        return fieldHighlight;
    }

    public void setFieldHighlight(Map<String, Boolean> fieldHighlight) {
        this.fieldHighlight = fieldHighlight;
    }
}
