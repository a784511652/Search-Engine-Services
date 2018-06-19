package com.united_iot.search.exception;

import com.united_iot.search.enumm.SearchNewEnum;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 13:40
 * @project_name： search
 * @Description ：
 */
public class SearchMyException extends RuntimeException {
    private String code;

    public SearchMyException(SearchNewEnum searchNewEnum) {
        super(searchNewEnum.getMsg());
        this.code = searchNewEnum.getCode();
    }

    public SearchMyException(SearchNewEnum searchNewEnum,String message) {
        super(message+searchNewEnum.getMsg());
        this.code = searchNewEnum.getCode();
    }
    public SearchMyException( String code,String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
