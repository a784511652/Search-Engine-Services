package com.united_iot.search.exception;

import com.united_iot.search.enumm.ResultEnum;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 11:40
 * @project_name： mc_elasticsearch
 * @Description ：
 */

public class MyException extends RuntimeException{

    private double code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public MyException() {

    }

    public MyException(double code,String message ) {
        super(message);
        this.code = code;
    }
    public double getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
