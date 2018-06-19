package com.united_iot.search.repository;



import com.united_iot.search.dataobject.IndexInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2018/1/15 0015
 * @time： 16:15
 * @project_name： search
 * @Description ：
 */
public interface IndexRepository extends JpaRepository<IndexInfo,String> {
}
