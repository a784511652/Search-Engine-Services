package com.united_iot.search.dataobject.mapper;


import com.united_iot.search.dataobject.IndexInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 15:25
 * @project_name： search
 * @Description ：
 */
public interface IndexMapper {
    @Insert("insert into `indexinfo`(index_name, appid) values (#{indexName, jdbcType=VARCHAR}, #{appid, jdbcType=VARCHAR})")
    int insertByObject(IndexInfo indexInfo);

    @Select("select * from indexinfo where indexName = #{name}")
    @Results({
            @Result(column = "appid", property = "appid"),
            @Result(column = "createTime", property = "time")
    })
    IndexInfo findByDBInfo(String name);

    @Select("select * from indexinfo where appid = #{appid}")
    @Results({
            @Result(column = "indexName", property = "name"),
            @Result(column = "createTime", property = "time")
    })
    List<IndexInfo> findByDBInfoDependAppid(String name);
}
