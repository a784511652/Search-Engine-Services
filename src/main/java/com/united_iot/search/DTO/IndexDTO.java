package com.united_iot.search.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2017/11/27 0027
 * @time： 11:15
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Data
public class IndexDTO {

    //库id
    //private Integer indexId;

    //库名称
    @NotNull(message="indexName不能为空")
    private String indexName;

    //库参照名称
    @NotNull(message="type不能为空")
    private String type;

    //分片数
   // private Integer shards;

    //副本数
  //  private Integer replicas;

    //备注
   // private String notes;

    //库当前状态
   // private boolean status = true;

    //字段集合
    @NotNull(message="字段不能为空")
    private List<FieldDTO> fieldInfo;


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

    public List<FieldDTO> getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(List<FieldDTO> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
}
