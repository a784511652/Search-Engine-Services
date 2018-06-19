package com.united_iot.search.service;


import com.united_iot.search.DTO.NewDTO.SearchByKeywordDTO;
import com.united_iot.search.DTO.NewDTO.SearchTableDTO;
import com.united_iot.search.DTO.searchDocument.RangeQueryDTO;
import com.united_iot.search.DTO.searchDocument.SearchDTO;
import com.united_iot.search.DTO.searchDocument.WeightSearchDTO;

import java.util.List;
import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:53
 * @project_name： mc_elasticsearch
 * @Description ：
 */
public interface SearchService {

    //根据关键词查询
    List<Map<String, Object>> searchDocument(SearchDTO searchDTO)throws Exception;

    List<Map<String, Object>> weightsearch(WeightSearchDTO searchDTO)throws Exception;

    List<Map<String, Object>> queryById(SearchDTO searchDTO);

    List<String> RangeQuery(RangeQueryDTO rangeQueryDTO);

    List RangeQueryAndSearch(RangeQueryDTO rangeQueryDTO);

    List SearchTable(String dbName);

    List<Map<String, Object>> searchByKeyowrd(SearchByKeywordDTO searchByKeyword) throws Exception;

}
