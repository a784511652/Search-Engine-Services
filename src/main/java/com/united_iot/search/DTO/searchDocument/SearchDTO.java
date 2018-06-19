package com.united_iot.search.DTO.searchDocument;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/28 0028
 * @time： 16:50
 * @project_name： demo
 * @Description ：
 */
@Data
public class SearchDTO {

    @NotNull(message="indexName不能为空")
    private String indexName;

    @NotNull(message="type不能为空")
    private String type;

    @NotNull(message="keyword不能为空")
    private String keyword;

    @NotNull(message="page不能为空")
    private Integer page;

    @NotNull(message="pageSize不能为空")
    private Integer pageSize;

    private String id;

    //最小时间
    private ZonedDateTime gt_time;


    private Map<String,Boolean> field;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getGt_time() {
        return gt_time;
    }

    public void setGt_time(ZonedDateTime gt_time) {
        this.gt_time = gt_time;
    }

    public Map<String, Boolean> getField() {
        return field;
    }

    public void setField(Map<String, Boolean> field) {
        this.field = field;
    }
}
