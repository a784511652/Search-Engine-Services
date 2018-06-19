package com.united_iot.search.controller;

import com.united_iot.search.DTO.deleteDocument.DeleteDTO;
import com.united_iot.search.service.DeleteService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jiahaowei
 * @date： 2017/11/28 0028
 * @time： 16:18
 * @project_name： search
 * @Description ：
 */
@RestController
public class DeleteDocumentController {

    @Autowired
    private DeleteService deleteService;

    @PostMapping({"/deldocument", "/3"})
    private ResultVO deleteDocument(@RequestBody DeleteDTO deleteDTO) throws Exception {
        deleteService.deleteDocument(deleteDTO);
        return ResultVOUtil.success();
    }

    @PostMapping({"/deleteIndex", "/33"})
    private ResultVO deleteIndex(@RequestBody DeleteDTO deleteDTO) throws Exception {
        deleteService.delIndex(deleteDTO.getIndexName());
        return ResultVOUtil.success();
    }

    @PostMapping({"/deleteByQuery", "/333"})
    private ResultVO deleteByQuery(@RequestBody DeleteDTO deleteDTO) throws Exception {
        deleteService.deleteByQuery(deleteDTO);
        return ResultVOUtil.success();
    }

    //慎用-删除所有索引
    @GetMapping({"/deleteAllIndex77889964", "/3333"})
    private ResultVO deleteAllIndex() throws Exception {
        deleteService.deleteAllIndex();
        return ResultVOUtil.success();
    }
}
