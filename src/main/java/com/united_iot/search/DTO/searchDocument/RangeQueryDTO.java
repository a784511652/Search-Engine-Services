package com.united_iot.search.DTO.searchDocument;

import lombok.Data;

/**
 * @auther jiahaowei
 * @date： 2017/12/7 0007
 * @time： 12:38
 * @project_name： demo
 * @Description ：
 */
@Data
public class RangeQueryDTO extends WeightSearchDTO {

    private String maxtime;

    private String mintime;

    private String targetParameter;

}
