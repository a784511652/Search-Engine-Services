package com.united_iot.search.controller.web;

import com.united_iot.search.DTO.deleteDocument.DeleteDTO;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchDBException;
import com.united_iot.search.exception.SearchException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.DeleteService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultSearchVO;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther jiahaowei
 * @date： 2018/1/17 0017
 * @time： 11:49
 * @project_name： search
 * @Description ：
 */
@RestController
public class DeleteController {

    @Autowired
    private DeleteService deleteService;


    @DeleteMapping("/search/{dbName}/{tbName}/{docId}")
    private ResultSearchVO deleteDocumentByID(@PathVariable("dbName") String dbName,
                                              @PathVariable("tbName") String tbName,
                                              @PathVariable("docId") String docId) throws Exception {
        try {
            DeleteDTO deleteDTO = new DeleteDTO();
            deleteDTO.setIndexName(dbName);
            deleteDTO.setType(tbName);
            deleteDTO.setId(docId);
            deleteService.deleteDocumentNew(deleteDTO);
        } catch (SearchMyException e) {
            throw new SearchMyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
        return ResultVOUtil.successsearch();
    }
}
