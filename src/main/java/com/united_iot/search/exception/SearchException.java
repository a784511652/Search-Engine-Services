package com.united_iot.search.exception;

import lombok.Getter;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 10:33
 * @project_name： search
 * @Description ：
 */
@Getter
public class SearchException extends RuntimeException {

    private String code;

    public SearchException( String code,String message) {
        super(message);
        this.code = code;
    }
    public SearchException(String message) {
        super(message);
    }
}
