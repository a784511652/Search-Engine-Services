package com.united_iot.search.DTO.NewDTO;

import com.united_iot.search.dataobject.IndexInfo;
import lombok.Data;

import java.util.List;

/**
 * @auther jiahaowei
 * @date： 2018/1/16 0016
 * @time： 14:10
 * @project_name： search
 * @Description ：
 */
@Data
public class SearchDBAppidDTO {

   private List<SearchDBAppidResultDTO> indexInfos;

   public List<SearchDBAppidResultDTO> getIndexInfos() {
      return indexInfos;
   }

   public void setIndexInfos(List<SearchDBAppidResultDTO> indexInfos) {
      this.indexInfos = indexInfos;
   }
}
