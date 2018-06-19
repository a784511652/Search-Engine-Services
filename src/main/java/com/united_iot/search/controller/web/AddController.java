package com.united_iot.search.controller.web;

import com.united_iot.search.DTO.NewDTO.AddDBDTO;
import com.united_iot.search.DTO.NewDTO.AddDocumentDTO;
import com.united_iot.search.DTO.NewDTO.AddTableDTO;
import com.united_iot.search.DTO.addDocument.AddDTO;
import com.united_iot.search.dataobject.mapper.IndexMapper;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchDBException;
import com.united_iot.search.exception.SearchException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.IndexService;
import com.united_iot.search.service.UpdateService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultSearchVO;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.coyote.http11.Constants.a;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 14:11
 * @project_name： search
 * @Description ：
 */
@RestController
public class AddController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private UpdateService updateService;

    //--创建DB
    @PostMapping({"/search/db", "/x1"})
    public ResultSearchVO addDB(@RequestBody AddDBDTO addDBDTO) throws Exception {

        try {
            indexService.CreateIndexNew(addDBDTO);
        } catch (SearchException e) {
            throw new SearchException("500", e.getMessage());
        } catch (SearchMyException e) {
            throw new SearchMyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
        return ResultVOUtil.successsearch();
    }


    //--创建Table
    @PostMapping({"/search/table", "/x2"})
    public ResultSearchVO addTable(@RequestBody AddTableDTO addTableDTO) {

        if (!indexService.isIndexExist(addTableDTO.getDbName())) {
            throw new SearchMyException(SearchNewEnum.fail_8);
        }

        if (indexService.isExistsType(addTableDTO.getDbName(), addTableDTO.getTableName())) {
            throw new SearchMyException(SearchNewEnum.fail_12);
        }

        //创建mappings
        indexService.CreateMappingsNew(addTableDTO);

        return ResultVOUtil.successsearch();
    }

    //--添加文档
    @PutMapping({"/search/document", "/x4"})
    public ResultSearchVO add(@RequestBody AddDocumentDTO addDocumentDTO) {

        try {
            updateService.AddDocumentNew(addDocumentDTO);

        } catch (SearchMyException e) {
            throw new SearchMyException(e.getCode(), e.getMessage());
        }catch (SearchDBException e) {
            throw new SearchDBException();
        }catch (Exception e) {
            throw new SearchException(e.getMessage());
        }

        return ResultVOUtil.successsearch();
    }


}
