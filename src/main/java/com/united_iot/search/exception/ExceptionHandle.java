package com.united_iot.search.exception;


import com.united_iot.search.utils.ResultVOUtil;
import com.iemylife.iot.logging.IotLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 13:14
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@ControllerAdvice
public class ExceptionHandle {
    @Autowired
    private IotLogger iotLogger;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object fande(Exception e, HttpServletResponse response) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            response.setStatus(400);
            iotLogger.warn("异常错误: " + myException.getCode() + "  " + myException.getMessage());
            return ResultVOUtil.error(myException.getCode(), myException.getMessage());
        } else if (e instanceof SearchMyException) {
            SearchMyException searchMyException = (SearchMyException) e;
            response.setStatus(400);
            iotLogger.warn("异常错误: " + searchMyException.getCode() + "  " + searchMyException.getMessage());
            return ResultVOUtil.errorsearch(searchMyException.getCode(), searchMyException.getMessage());
        } else if (e instanceof SearchException) {
            response.setStatus(500);
            iotLogger.error("异常服务器错误: " + e.getMessage());

            return ResultVOUtil.errorsearch("500", e.getMessage());
        }else if (e instanceof SearchDBException) {
            response.setStatus(404);
            iotLogger.warn("异常错误: " + "对应的DBName下没有数据存在");
            return ResultVOUtil.successsearch_DB();
        } else {
            response.setStatus(500);
            iotLogger.error("异常服务器错误: " + e.getMessage());
            return ResultVOUtil.exception(e.getMessage());
        }
    }
}
