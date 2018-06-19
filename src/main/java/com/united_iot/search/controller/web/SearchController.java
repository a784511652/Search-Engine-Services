package com.united_iot.search.controller.web;


import com.united_iot.search.DTO.NewDTO.SearchByKeywordDTO;
import com.united_iot.search.DTO.NewDTO.SearchDBAppidResultDTO;
import com.united_iot.search.DTO.NewDTO.SearchDBDTO;
import com.united_iot.search.dataobject.IndexInfo;
import com.united_iot.search.dataobject.mapper.IndexMapper;
import com.united_iot.search.exception.SearchDBException;
import com.united_iot.search.exception.SearchException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.SearchService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultSearchSuccessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 11:47
 * @project_name： search
 * @Description ：
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexMapper indexMapper;

    //--查询DB
    @GetMapping("/search/dbs/{dbName}")
    public SearchDBDTO searchDB(@PathVariable("dbName") String dbName) {
        try {
            SearchDBDTO searchDBDTO = new SearchDBDTO();
            IndexInfo indexInfo = indexMapper.findByDBInfo(dbName);


            if (indexInfo == null) {
                throw new SearchDBException();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ctime = formatter.format(indexInfo.getTime());

            searchDBDTO.setAppid(indexInfo.getAppid());
            searchDBDTO.setCreateTime(ctime);

            return searchDBDTO;
        } catch (SearchDBException e) {
            throw new SearchDBException();
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
    }

    //--查询DB
    @GetMapping("/search/dbs")
    public List<SearchDBAppidResultDTO> searchDBDependAppid(@RequestParam("appid") String appid) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<IndexInfo> indexInfos = indexMapper.findByDBInfoDependAppid(appid);
        List<SearchDBAppidResultDTO> searchDBAppidResultDTOList = new ArrayList<>();

        for (IndexInfo indexInfo : indexInfos) {
            SearchDBAppidResultDTO searchDBAppidResultDTO = new SearchDBAppidResultDTO();
            String ctime = formatter.format(indexInfo.getTime());
            searchDBAppidResultDTO.setDbName(indexInfo.getName());
            searchDBAppidResultDTO.setCreateTime(ctime);
            searchDBAppidResultDTOList.add(searchDBAppidResultDTO);
        }
        return searchDBAppidResultDTOList;
    }


    //--查询DBTable
    @GetMapping("/search/{dbName}")
    public List searchTable(@PathVariable("dbName") String dbName) {

        return searchService.SearchTable(dbName);
    }


    //关键字全文搜索
    @PostMapping("/search/keywords")
    public ResultSearchSuccessVO searchKeyword(@RequestBody SearchByKeywordDTO searchByKeywordDTO) throws Exception {
        try {
            List<Map<String, Object>> list = searchService.searchByKeyowrd(searchByKeywordDTO);

            return ResultVOUtil.successsearch_search(list);
        } catch (SearchMyException e) {
            throw new SearchMyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
    }


}
