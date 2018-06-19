package com.united_iot.search.controller;

import com.united_iot.search.DTO.IndexDTO;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.service.DeleteService;
import com.united_iot.search.service.IndexService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @auther jiahaowei
 * @date： 2017/11/26 0026
 * @time： 20:43
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@RestController
public class CreateIndexController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private DeleteService deleteService;


    @PostMapping({"/createindex", "/1"})
    private ResultVO CreateIndexAndMappings(@Valid @RequestBody IndexDTO indexDTO, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new MyException(ResultEnum.fail_12.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        //创建Index
        indexService.CreateIndex(indexDTO.getIndexName());

        try {
            //创建mappings
            indexService.CreateMappings(indexDTO);
        }catch (MyException e){

            deleteService.delIndex(indexDTO.getIndexName());

            throw new MyException(e.getCode(),e.getMessage());
        }


        return ResultVOUtil.success();
    }


}
