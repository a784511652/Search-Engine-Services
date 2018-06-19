package com.united_iot.search.DTO.NewDTO;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 13:29
 * @project_name： search
 * @Description ：
 */
@lombok.Data
public class SearchDBDTO {

    private String appid;

    private String createTime;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
