package com.united_iot.search.enumm;

import lombok.Getter;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 11:47
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Getter
public enum ResultEnum {
    fail_1(400.1, "Index已经存在"),
    fail_2(400.2, "索引名错误(只能输入小写，数字，_)"),
    fail_3(400.3, "传入的字段集合为空，请您自己查看协议"),
    fail_4(400.4, "字段名称长度不能大于30字符"),
    fail_5(400.5, "不能有重复字段名称"),
    fail_6(400.6, "字段类型不能为空"),
    fail_7(400.7, "请求字段错误"),
    fail_8(400.8, "Index不存在"),
    fail_9(400.9, "Index下的type不存在"),
    fail_10(400.10, "ID不存在"),
    fail_11(400.11, "page 以及 pageSize不应≤0"),
    fail_12(400.12, "请检查参数是否正确（看看是不是少了~！）"),
    fail_13(400.13, "fieldType应从text、date、keyword选择"),
    fail_14(400.14, "搜索的字段超量"),
    fail_15(400.15, "高亮字段与搜索权重字段不一致"),
    ;
    private double code;

    private String msg;

    ResultEnum(double code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public double getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
