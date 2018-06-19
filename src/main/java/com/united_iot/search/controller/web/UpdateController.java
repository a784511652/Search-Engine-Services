package com.united_iot.search.controller.web;

import com.united_iot.search.DTO.NewDTO.AddTableDTO;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.IndexService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultSearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 11:24
 * @project_name： search
 * @Description ：
 */
@RestController
public class UpdateController {

    @Autowired
    private IndexService indexService;


    //--修改Table
    @PutMapping({"/search/table", "/x3"})
    public ResultSearchVO addTable(@RequestBody AddTableDTO addTableDTO) {


        if (!indexService.isExistsType(addTableDTO.getDbName(), addTableDTO.getTableName())) {
            throw new SearchMyException(SearchNewEnum.fail_9);
        }

        //更新mappings
        indexService.CreateMappingsNew(addTableDTO);
        return ResultVOUtil.successsearch();

    }
}
