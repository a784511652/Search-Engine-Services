package com.united_iot.search.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 20:32
 * @project_name： search
 * @Description ：
 */
@Entity
@DynamicUpdate
@Table(name = "indexinfo")
@Data
public class IndexInfo {

    @Id
    @Column(name = "indexName")
    private String name;

    @Column(name = "appid")
    private String appid;

    @Column(name = "createTime")
    private Date time;
}
