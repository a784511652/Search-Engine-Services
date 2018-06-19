package com.united_iot.search.DTO.NewDTO;

import lombok.Data;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 14:19
 * @project_name： search
 * @Description ：
 */
@Data
public class SearchDBAppidResultDTO {
    private String dbName;

    private String createTime;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
