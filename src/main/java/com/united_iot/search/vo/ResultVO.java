package com.united_iot.search.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 15:07
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Data
public class ResultVO implements Serializable {

    //数值，1-3
    private String result;

    // 错误码
    private double code;

    // 提示信息
    private Object msg;

    // 提示信息
    private Object returnValue;


    // 具体内容
    private long ts = System.currentTimeMillis() / 1000;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getCode() {
        return code;
    }

    public void setCode(double code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
