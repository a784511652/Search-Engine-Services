package com.united_iot.search.utils;


import com.united_iot.search.vo.ResultSearchSuccessVO;
import com.united_iot.search.vo.ResultSearchVO;
import com.united_iot.search.vo.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 15:22
 * @project_name： mc_elasticsearch
 * @Description ：
 */
public class ResultVOUtil {
    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setResult("1");
        resultVO.setCode(100);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO successtwo(long a) {
        ResultVO resultVO = new ResultVO();
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("docmentVersion", a);
        resultVO.setResult("1");
        resultVO.setCode(100);
        resultVO.setMsg("success");
        resultVO.setReturnValue(map);
        return resultVO;
    }

    public static ResultVO successthree(List<Map<String, Object>> a) {
        ResultVO resultVO = new ResultVO();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docNum", a.get(a.size() - 1).get("total"));
        a.remove(a.size() - 1);
        map.put("content", a);
        resultVO.setResult("1");
        resultVO.setCode(100);
        resultVO.setMsg("success");
        resultVO.setReturnValue(map);
        return resultVO;
    }

    public static ResultVO successfour(List<String> a) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResult("1");
        resultVO.setCode(100);
        resultVO.setMsg("success");
        resultVO.setReturnValue(a);
        return resultVO;
    }

    public static ResultVO error(double a, String object) {
        ResultVO resultVO = new ResultVO();
        Map<String, String> map = new HashMap<String, String>();
        map.put("errorMessage", object);
        resultVO.setResult("2");
        resultVO.setCode(a);
        resultVO.setMsg(map);
        return resultVO;
    }


    public static ResultSearchVO errorsearch(String a, String object) {
        ResultSearchVO resultSearchVO = new ResultSearchVO();
        resultSearchVO.setErrorCode(a);
        resultSearchVO.setErrorMessage(object);
        return resultSearchVO;
    }

    public static ResultSearchVO successsearch() {
        return null;
    }

    public static ResultSearchSuccessVO successsearch_search(List<Map<String, Object>> a) {
        ResultSearchSuccessVO resultVO = new ResultSearchSuccessVO();
        resultVO.setDocNum(a.get(a.size() - 1).get("total"));
        a.remove(a.size() - 1);
        resultVO.setDocInfo(a);

        return resultVO;
    }

    public static ResultSearchVO successsearch_DB() {
        return null;
    }

    public static ResultVO exception(String a) {
        ResultVO restult = new ResultVO();
        restult.setResult("3");
        restult.setCode(500);
        restult.setMsg(a);
        return restult;
    }
}
