package com.united_iot.search.service;

import com.united_iot.search.DTO.NewDTO.AddDocumentDTO;
import com.united_iot.search.DTO.addDocument.AddDTO;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:56
 * @project_name： mc_elasticsearch
 * @Description ：
 */
public interface UpdateService {

    //创建文档  更新就是新插入一条数据
    long AddDocument(AddDTO addDTO) throws Exception;

    void AddDocumentNew(AddDocumentDTO addDTO) throws Exception;
}
