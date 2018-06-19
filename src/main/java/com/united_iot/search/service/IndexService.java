package com.united_iot.search.service;

import com.united_iot.search.DTO.IndexDTO;
import com.united_iot.search.DTO.NewDTO.AddDBDTO;
import com.united_iot.search.DTO.NewDTO.AddTableDTO;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:50
 * @project_name： mc_elasticsearch
 * @Description ：要小写  保证返回的version是1 （要不然就相当于更新）
 */
public interface IndexService {


    //创建索引
    void CreateIndex(String indexName) throws Exception;

    //创建mapping
    void CreateMappings(IndexDTO indexName) throws Exception;

    //判断索引是否存在
    boolean isIndexExist(String indexName);

    //判断索引下的类型是否存在
    boolean isExistsType(String indexName,String indexType);


    //创建索引(新 脱体原本的项目)
    void CreateIndexNew(AddDBDTO addDBDTO) throws Exception;

    //创建mapping
    void CreateMappingsNew(AddTableDTO addTableDTO) ;


}
