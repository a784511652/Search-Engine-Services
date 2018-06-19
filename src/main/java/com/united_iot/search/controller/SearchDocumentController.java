package com.united_iot.search.controller;

import com.united_iot.search.DTO.searchDocument.RangeQueryDTO;
import com.united_iot.search.DTO.searchDocument.SearchDTO;
import com.united_iot.search.DTO.searchDocument.WeightSearchDTO;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.service.SearchService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2017/11/28 0028
 * @time： 16:54
 * @project_name： search
 * @Description ：
 */
@RestController
public class SearchDocumentController {

    @Autowired
    private SearchService searchService;


    @PostMapping({"/search", "/4"})
    private ResultVO search(@Valid @RequestBody SearchDTO searchDTO, BindingResult bindingResult) throws Exception {


        if (bindingResult.hasErrors()) {
            throw new MyException(ResultEnum.fail_12.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        //List list = searchService.searchDocument(searchDTO);
        List list = searchService.searchDocument(searchDTO);

        return ResultVOUtil.successthree(list);
    }

    @PostMapping({"/searchID", "/44"})
    private ResultVO searchID(@RequestBody SearchDTO searchDTO) throws Exception {

        List list = searchService.queryById(searchDTO);

        return ResultVOUtil.successthree(list);
    }


    @PostMapping({"/weightsearch", "/444"})
    private ResultVO weightsearch(@Valid @RequestBody WeightSearchDTO WeightSearchDTO, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new MyException(ResultEnum.fail_12.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        List list = searchService.weightsearch(WeightSearchDTO);

        return ResultVOUtil.successthree(list);
    }


    @PostMapping({"/rangequery", "/4444"})
    private ResultVO RangeQuery(@RequestBody RangeQueryDTO rangeQueryDTO) throws Exception {


        List list = searchService.RangeQuery(rangeQueryDTO);

        return ResultVOUtil.successfour(list);
    }
}
