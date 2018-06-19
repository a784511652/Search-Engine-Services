package com.united_iot.search.service.Impl;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.united_iot.search.DTO.NewDTO.FieldNewDTO;
import com.united_iot.search.DTO.NewDTO.SearchByKeywordDTO;
import com.united_iot.search.DTO.NewDTO.SearchTableDTO;
import com.united_iot.search.DTO.searchDocument.RangeQueryDTO;
import com.united_iot.search.DTO.searchDocument.SearchDTO;
import com.united_iot.search.DTO.searchDocument.WeightSearchDTO;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.IndexService;
import com.united_iot.search.service.SearchService;
import com.iemylife.iot.logging.IotLogger;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:53
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private IndexService indexService;

    @Autowired
    private TransportClient client;

    @Autowired
    private IotLogger iotLogger;

    public List<Map<String, Object>> weightsearch(WeightSearchDTO weightSearchDTO) throws Exception {

        //更新名称
        weightSearchDTO.setIndexName("pri:"+weightSearchDTO.getIndexName());

        if (!indexService.isIndexExist(weightSearchDTO.getIndexName())) {
            throw new MyException(ResultEnum.fail_8);
        }
        if (!indexService.isExistsType(weightSearchDTO.getIndexName(), weightSearchDTO.getType())) {
            throw new MyException(ResultEnum.fail_9);
        }
        if (weightSearchDTO.getPage() <= 0 || weightSearchDTO.getPageSize() <= 0) {
            throw new MyException(ResultEnum.fail_11);
        }

        for (String key : weightSearchDTO.getFieldWeight().keySet()) {
            if (!weightSearchDTO.getFieldHighlight().containsKey(key) || weightSearchDTO.getFieldHighlight().size() != weightSearchDTO.getFieldWeight().size()) {
                throw new MyException(ResultEnum.fail_15);
            }
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        for (String key : weightSearchDTO.getFieldHighlight().keySet()) {
            if (weightSearchDTO.getFieldHighlight().get(key)) {
                highlightBuilder.field(key);
            }
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : weightSearchDTO.getFieldWeight().keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, weightSearchDTO.getKeyword()).boost(weightSearchDTO.getFieldWeight().get(key)));
        }

      /*  RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("date");
        rangeQueryBuilder.from("2017-02-06 00:00:12");
        rangeQueryBuilder.to("2017-12-06 00:00:12");

        boolQueryBuilder.filter(rangeQueryBuilder);*/

        SearchRequestBuilder builder = client.prepareSearch(weightSearchDTO.getIndexName())
                .setTypes(weightSearchDTO.getType())
                .highlighter(highlightBuilder)
                //对所有碎片执行查询，但只返回足够的信息(而不是文档内容)。
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom((weightSearchDTO.getPage() - 1) * weightSearchDTO.getPageSize())
                .setSize(weightSearchDTO.getPageSize());

        SearchResponse response = builder.get();
        SearchHits hits = response.getHits();


        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapDoc = new HashMap<String, Object>();
        mapDoc.put("total", hits.getTotalHits());

        for (SearchHit hit : hits) {
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData = hit.getSource();
            Map<String, HighlightField> mapHigthLight = new HashMap<String, HighlightField>();
            mapHigthLight = hit.getHighlightFields();

            for (String key : mapHigthLight.keySet()) {

                String value = mapHigthLight.get(key).toString()
                        .substring(mapHigthLight.get(key).toString().indexOf("fragments[[") + 11,
                                mapHigthLight.get(key).toString().length() - 2);

                mapData.put(key, value);

            }
            mapData.put("id", hit.getId());
            listData.add(mapData);
        }

        listData.add(mapDoc);
        return listData;

    }

    public List<Map<String, Object>> queryById(SearchDTO searchDTO) {

        try {

            if (!indexService.isIndexExist(searchDTO.getIndexName())) {
                throw new MyException(ResultEnum.fail_8);
            }

            if (!indexService.isExistsType(searchDTO.getIndexName(), searchDTO.getType())) {
                throw new MyException(ResultEnum.fail_9);
            }
        } catch (Exception e) {
            throw new MyException(ResultEnum.fail_12);
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        GetResponse getResponse = client.prepareGet().setIndex(searchDTO.getIndexName())
                .setType(searchDTO.getType())
                .setId(searchDTO.getId())
                .get();
        result.add(getResponse.getSource());

        return result;
    }

    public List<Map<String, Object>> searchDocument(SearchDTO searchDTO) throws Exception {

        //更新名称
        searchDTO.setIndexName("pri:"+searchDTO.getIndexName());

        if (!indexService.isIndexExist(searchDTO.getIndexName())) {
            throw new MyException(ResultEnum.fail_8);
        }

        if (!indexService.isExistsType(searchDTO.getIndexName(), searchDTO.getType())) {
            throw new MyException(ResultEnum.fail_9);
        }
        if (searchDTO.getPage() <= 0 || searchDTO.getPageSize() <= 0) {
            throw new MyException(ResultEnum.fail_11);
        }

        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

        List<String> fieldNameList = new ArrayList<String>();
        fieldNameList.addAll(searchDTO.getField().keySet());

        SearchHits hits = null;
        switch (searchDTO.getField().size()) {
            case 1:
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightBuilder.preTags("<em>");
                highlightBuilder.postTags("</em>");
                for (String key : searchDTO.getField().keySet()) {
                    if (searchDTO.getField().get(key)) {
                        highlightBuilder.field(key);
                    }
                }
                QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchDTO.getKeyword(), fieldNameList.get(0));
                SearchResponse response = client.prepareSearch(searchDTO.getIndexName()).setQuery(queryBuilder)
                        .setTypes(searchDTO.getType()).highlighter(highlightBuilder)
                        .setFrom((searchDTO.getPage() - 1) * searchDTO.getPageSize())
                        .setSize(searchDTO.getPageSize()).get();
                hits = response.getHits();
                break;

            case 2:
                highlightBuilder = new HighlightBuilder();
                highlightBuilder.preTags("<em>");
                highlightBuilder.postTags("</em>");
                for (String key : searchDTO.getField().keySet()) {
                    if (searchDTO.getField().get(key)) {
                        highlightBuilder.field(key);
                    }
                }
                queryBuilder = QueryBuilders.multiMatchQuery(searchDTO.getKeyword(), fieldNameList.get(0), fieldNameList.get(1));
                response = client.prepareSearch(searchDTO.getIndexName()).setQuery(queryBuilder)
                        .setTypes(searchDTO.getType()).highlighter(highlightBuilder)
                        .setFrom((searchDTO.getPage() - 1) * searchDTO.getPageSize())
                        .setSize(searchDTO.getPageSize()).get();
                hits = response.getHits();
                break;

            case 3:
                highlightBuilder = new HighlightBuilder();
                highlightBuilder.preTags("<em>");
                highlightBuilder.postTags("</em>");
                for (String key : searchDTO.getField().keySet()) {
                    if (searchDTO.getField().get(key)) {
                        highlightBuilder.field(key);
                    }
                }
                queryBuilder = QueryBuilders.multiMatchQuery(searchDTO.getKeyword(), fieldNameList.get(0), fieldNameList.get(1), fieldNameList.get(2));
                response = client.prepareSearch(searchDTO.getIndexName()).setQuery(queryBuilder)
                        .setTypes(searchDTO.getType()).highlighter(highlightBuilder)
                        .setFrom((searchDTO.getPage() - 1) * searchDTO.getPageSize())
                        .setSize(searchDTO.getPageSize()).get();
                hits = response.getHits();
                break;

            default:
                throw new MyException(ResultEnum.fail_14);
        }

        iotLogger.info("搜到 " + hits.totalHits() + " 记录");
        Map<String, Object> mapDoc = new HashMap<String, Object>();
        mapDoc.put("total", hits.getTotalHits());
        for (SearchHit hit : hits) {
            Map<String, Object> mapData = new HashMap<String, Object>();

            mapData = hit.getSource();
            iotLogger.debug("数据map的数据个数" + mapData.size());
            Map<String, HighlightField> mapHigthLight = new HashMap<String, HighlightField>();
            mapHigthLight = hit.getHighlightFields();


            for (String key : mapHigthLight.keySet()) {

                String value = mapHigthLight.get(key).toString()
                        .substring(mapHigthLight.get(key).toString().indexOf("fragments[[") + 11,
                                mapHigthLight.get(key).toString().length() - 2);

                mapData.put(key, value);
            }
            mapData.put("id", hit.getId());
            listData.add(mapData);


        }
        listData.add(mapDoc);
        return listData;
    }


    public List<String> RangeQuery(RangeQueryDTO rangeQueryDTO) {

        if (!indexService.isIndexExist(rangeQueryDTO.getIndexName())) {
            throw new MyException(ResultEnum.fail_8);
        }
        if (!indexService.isExistsType(rangeQueryDTO.getIndexName(), rangeQueryDTO.getType())) {
            throw new MyException(ResultEnum.fail_9);
        }
        if (rangeQueryDTO.getPage() <= 0 || rangeQueryDTO.getPageSize() <= 0) {
            throw new MyException(ResultEnum.fail_11);
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        for (String key : rangeQueryDTO.getFieldHighlight().keySet()) {
            if (rangeQueryDTO.getFieldHighlight().get(key)) {
                highlightBuilder.field(key);
            }
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : rangeQueryDTO.getFieldWeight().keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, rangeQueryDTO.getKeyword()).boost(rangeQueryDTO.getFieldWeight().get(key)));
        }

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeQueryDTO.getTargetParameter());
        rangeQueryBuilder.from(rangeQueryDTO.getMintime());
        rangeQueryBuilder.to(rangeQueryDTO.getMaxtime());
        rangeQueryBuilder.includeLower(true);
        rangeQueryBuilder.includeUpper(true);

        boolQueryBuilder.filter(rangeQueryBuilder);

        SearchRequestBuilder builder = client.prepareSearch(rangeQueryDTO.getIndexName())
                .setTypes(rangeQueryDTO.getType())
                .setQuery(boolQueryBuilder)
                .setFrom((rangeQueryDTO.getPage() - 1) * rangeQueryDTO.getPageSize())
                .setSize(rangeQueryDTO.getPageSize());


        SearchResponse response = builder.get();
        SearchHits hits = response.getHits();

        List<String> docList = new ArrayList<String>();
        for (SearchHit hit : hits) {
            docList.add(hit.getSourceAsString());
        }

        return docList;
    }


    public List RangeQueryAndSearch(RangeQueryDTO rangeQueryDTO) {

        return null;
    }

    //--------------------------------------new--------------------------------------------------------
    public List SearchTable(String dbName) {
        //1-创建实体
        List<SearchTableDTO> searchTableDTOArrayList = new ArrayList<>();

        if (!indexService.isIndexExist(dbName)) {
            return searchTableDTOArrayList;
        }

        //2-创建 api 获得mapping信息
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        GetMappingsRequestBuilder getMappingsRequestBuilder = indicesAdminClient.prepareGetMappings(dbName);
        GetMappingsResponse response = getMappingsRequestBuilder.get();


        for (ObjectCursor<String> key : response.getMappings().keys()) {
            ImmutableOpenMap<String, MappingMetaData> mapping = response.getMappings().get(key.value);

            //3-遍历获得单个mapping的信息
            for (ObjectCursor<String> key2 : mapping.keys()) {
                List<FieldNewDTO> fieldNewDTOListNew = new ArrayList<>();
                SearchTableDTO searchTableDTO = new SearchTableDTO();
                searchTableDTO.setTableName(key2.value);

                try {
                    Map<String, Map<String, String>> result = (Map) mapping.get(key2.value).sourceAsMap().get("properties");

                    //4-遍历获得key 再对value{type=text, analyzer=ik_max_word}进行遍历
                    for (Map.Entry<String, Map<String, String>> entry : result.entrySet()) {
                        FieldNewDTO fieldNewDTO = new FieldNewDTO();
                        fieldNewDTO.setFieldName(entry.getKey());

                        for (Map.Entry<String, String> valueEntry : entry.getValue().entrySet()) {
                            if (valueEntry.getValue().equals("text") || valueEntry.getValue().equals("keyword") || valueEntry.getValue().equals("date")) {
                                fieldNewDTO.setFieldType(valueEntry.getValue());
                            }
                            if (valueEntry.getKey().equals("analyzer")) {
                                fieldNewDTO.setAnalyzer(valueEntry.getValue());
                            }
                        }
                        fieldNewDTOListNew.add(fieldNewDTO);
                    }
                    searchTableDTO.setFields(fieldNewDTOListNew);
                } catch (IOException e) {
                    throw new SearchException(e.getMessage());
                }
                searchTableDTOArrayList.add(searchTableDTO);
            }
        }
        return searchTableDTOArrayList;
    }

    public List<Map<String, Object>> searchByKeyowrd(SearchByKeywordDTO searchByKeyword) throws Exception {


        if (!indexService.isIndexExist(searchByKeyword.getDbName())) {
            throw new SearchMyException(SearchNewEnum.fail_8);
        }
        if (!indexService.isExistsType(searchByKeyword.getDbName(), searchByKeyword.getTbName())) {
            throw new SearchMyException(SearchNewEnum.fail_9);
        }
        if (searchByKeyword.getPage() <= 0 || searchByKeyword.getPageSize() <= 0) {
            throw new SearchMyException(SearchNewEnum.fail_11);
        }


        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        for (String key : searchByKeyword.getFieldHighlight().keySet()) {
            if (searchByKeyword.getFieldHighlight().get(key)) {
                highlightBuilder.field(key);
            }
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : searchByKeyword.getFieldHighlight().keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, searchByKeyword.getKeyword()));
        }



        SearchRequestBuilder builder = client.prepareSearch(searchByKeyword.getDbName())
                .setTypes(searchByKeyword.getTbName())
                .highlighter(highlightBuilder)
                //对所有碎片执行查询，但只返回足够的信息(而不是文档内容)。
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom((searchByKeyword.getPage() - 1) * searchByKeyword.getPageSize())
                .setSize(searchByKeyword.getPageSize());

        SearchResponse response = builder.get();
        SearchHits hits = response.getHits();


        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapDoc = new HashMap<String, Object>();
        mapDoc.put("total", hits.getTotalHits());

        for (SearchHit hit : hits) {
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData = hit.getSource();
            Map<String, HighlightField> mapHigthLight = new HashMap<String, HighlightField>();
            mapHigthLight = hit.getHighlightFields();

            for (String key : mapHigthLight.keySet()) {

                String value = mapHigthLight.get(key).toString()
                        .substring(mapHigthLight.get(key).toString().indexOf("fragments[[") + 11,
                                mapHigthLight.get(key).toString().length() - 2);

                mapData.put(key, value);

            }
            mapData.put("id", hit.getId());
            listData.add(mapData);
        }

        listData.add(mapDoc);
        return listData;

    }


}

