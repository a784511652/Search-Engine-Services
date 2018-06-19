package com.united_iot.search.DTO.NewDTO;

import lombok.Data;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 14:26
 * @project_name： search
 * @Description ：
 */
@Data
public class AddDBDTO {

    private String appid;

    private String dbName;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


}
