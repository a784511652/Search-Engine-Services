package com.united_iot.search.DTO.searchDocument;

import lombok.Data;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/12/5 0005
 * @time： 10:58
 * @project_name： demo
 * @Description ：
 */
@Data
public class WeightSearchDTO extends SearchDTO {

    private Map<String,Boolean> fieldHighlight;

    private Map<String,Float> fieldWeight;

    public Map<String, Boolean> getFieldHighlight() {
        return fieldHighlight;
    }

    public void setFieldHighlight(Map<String, Boolean> fieldHighlight) {
        this.fieldHighlight = fieldHighlight;
    }

    public Map<String, Float> getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(Map<String, Float> fieldWeight) {
        this.fieldWeight = fieldWeight;
    }
}
