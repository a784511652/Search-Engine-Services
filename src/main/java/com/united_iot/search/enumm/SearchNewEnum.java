package com.united_iot.search.enumm;

import lombok.Getter;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 13:58
 * @project_name： search
 * @Description ：
 */
@Getter
public enum SearchNewEnum {
    fail_1("400.1", "DB已经存在"),
    fail_2("400.2", "db名称不符合"),

    //*****
    fail_3("400.3", "传入的字段集合为空，请您自己查看协议"),
    fail_4("400.4", "字段名称长度不能大于30字符"),
    fail_5("400.5", "不能有重复字段名称"),
    fail_6("400.6", "字段类型不能为空"),


    fail_7("400.7", "字段错误"),
    fail_8("400.8", "DB不存在"),
    fail_9("400.9", "Table不存在"),


    //***
    fail_10("400.10", "ID不存在"),
    fail_11("400.11", "page 以及 pageSize不应≤0"),

    fail_12("400.12", "Table已存在"),

    //***
    fail_13("400.13", "fieldType应从text、date、keyword选择"),
    fail_14("400.14", "搜索的字段超量"),
    fail_15("400.15", "高亮字段与搜索权重字段不一致"),

    fail_16("400.16", "appid为空"),
    fail_17("400.17", "该字段已存在"),
    ;
    private String code;

    private String msg;

    SearchNewEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
