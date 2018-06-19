package com.united_iot.search.service;

import com.united_iot.search.DTO.deleteDocument.DeleteDTO;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:53
 * @project_name： mc_elasticsearch
 * @Description ：
 */
public interface DeleteService {

    //根据ID删除文档
    void deleteDocument(DeleteDTO deleteDTO)throws Exception;

    //删除单个索引
    void delIndex(String IndexName) throws Exception;

    //根据条件删除记录
    void deleteByQuery(DeleteDTO deleteDTO) throws Exception;

    //删除所有搜索索引
    void deleteAllIndex();

    //--------new--------------
    void deleteDocumentNew(DeleteDTO deleteDTO) throws Exception;
}
