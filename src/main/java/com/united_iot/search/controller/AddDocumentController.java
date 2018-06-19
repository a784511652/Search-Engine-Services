package com.united_iot.search.controller;

import com.united_iot.search.DTO.addDocument.AddDTO;
import com.united_iot.search.service.UpdateService;
import com.united_iot.search.utils.ResultVOUtil;
import com.united_iot.search.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jiahaowei
 * @date： 2017/11/27 0027
 * @time： 18:48
 * @project_name： demo
 * @Description ：
 */
@RestController
public class AddDocumentController {

    @Autowired
    private UpdateService updateService;

    @PostMapping({"/adddocument", "/2"})
    public ResultVO add(@RequestBody AddDTO addDTO) throws Exception {

        Long result = updateService.AddDocument(addDTO);

        return ResultVOUtil.successtwo(result);
    }
}
