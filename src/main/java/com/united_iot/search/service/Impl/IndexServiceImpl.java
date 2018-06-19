package com.united_iot.search.service.Impl;

import com.united_iot.search.DTO.FieldDTO;
import com.united_iot.search.DTO.IndexDTO;
import com.united_iot.search.DTO.NewDTO.AddDBDTO;
import com.united_iot.search.DTO.NewDTO.AddTableDTO;
import com.united_iot.search.DTO.NewDTO.FieldNewDTO;

import com.united_iot.search.dataobject.IndexInfo;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.repository.IndexRepository;
import com.united_iot.search.service.DeleteService;
import com.united_iot.search.service.IndexService;
import com.iemylife.iot.logging.IotLogger;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:50
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private TransportClient client;


    @Autowired
    private DeleteService deleteService;

    @Autowired
    private IotLogger iotLogger;


    public void CreateIndex(String indexName) throws Exception {

        //正则匹配只能是小写以及数字和_
        Pattern p = Pattern.compile("[a-z0-9_]*");
        Matcher m = p.matcher(indexName);

        if (!m.matches()) {
            throw new MyException(ResultEnum.fail_2);
        }

        //更改名称
        indexName = "pri:" + indexName;

        if (!isIndexExist(indexName)) {
            CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).get();
            client.admin().indices().prepareUpdateSettings(indexName).setSettings(Settings.builder()
                    .put("index.mapper.dynamic", "false")).get();
            //表示是否成功
            iotLogger.info("【创建成功】--->" + indexName + ":" + response.isAcknowledged());
        } else {
            throw new MyException(ResultEnum.fail_1);
        }
    }


    //创建mapping（相同于创建表）
    public void CreateMappings(IndexDTO indexDTO) throws Exception {
        //更新名称
        indexDTO.setIndexName("pri:" + indexDTO.getIndexName());


        XContentBuilder builder = getMapping(indexDTO);
        iotLogger.info("【创建格式为】：" + builder.string());
        PutMappingRequest mappingRequest = Requests.putMappingRequest(indexDTO.getIndexName()).source(builder).type(indexDTO.getType());
        client.admin().indices().putMapping(mappingRequest).actionGet();

    }

    //判断索引是否存在
    public boolean isIndexExist(String indexName) {
        IndicesExistsResponse inExistsResponse = client
                .admin()
                .indices()
                .exists(new IndicesExistsRequest(indexName))
                .actionGet();
        return inExistsResponse.isExists();
    }


    private XContentBuilder getMapping(IndexDTO indexDTO) throws Exception {
        XContentBuilder mapping = XContentFactory.jsonBuilder();
        //开始---
        mapping.startObject();

        // 字段名称集合, 不能有重复字段名称
        List<String> fieldNameList = new ArrayList<>();

        //1-判断fieldList是否为空
        if (indexDTO.getFieldInfo() == null || indexDTO.getFieldInfo().size() == 0) {
            throw new MyException(ResultEnum.fail_3);
        }

        //2-遍历数据
        mapping.startObject("properties");
        for (FieldDTO field : indexDTO.getFieldInfo()) {

            if (field.getFieldName().length() > 30) {
                throw new MyException(ResultEnum.fail_4);
            }

            if (fieldNameList.contains(field.getFieldName())) {
                throw new MyException(ResultEnum.fail_5);
            }

            //3-判断是否为date类型
            mapping.startObject(field.getFieldName());
            if (field.getFieldType() != null) {
                mapping.field("type", field.getFieldType());

                if (field.getFieldType().equals("date")) {
                    mapping.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
                }
            } else {
                throw new MyException(ResultEnum.fail_6);
            }

            //4-判断权重
            if (field.getWeight() == null) {
                mapping.field("boost", "1.0");
            } else {
                mapping.field("boost", field.getWeight().toString());
            }

            //5-分词判断

            if (field.getAnalysis() != null) {
                if (field.getFieldType().equals("text")) {
                    mapping.field("analyzer", "ik_max_word");
                    mapping.field("search_analyzer", "ik_max_word");
                }
            }

            //结束---
            mapping.endObject();
            fieldNameList.add(field.getFieldName());


            if (!field.getFieldType().equals("text") && !field.getFieldType().equals("keyword") && !field.getFieldType().equals("date")) {
                deleteService.delIndex(indexDTO.getIndexName());
                throw new MyException(ResultEnum.fail_13);
            }

        }

        mapping.endObject();
        mapping.field("dynamic", "strict");
        mapping.endObject();
        return mapping;
    }

    @Override
    public boolean isExistsType(String indexName, String indexType) {
        TypesExistsResponse response =
                client.admin().indices()
                        .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)
                        ).actionGet();
        return response.isExists();
    }


    //---------------------------------------new--------------------------------------------------------------------------------------
    @Override
    public void CreateIndexNew(AddDBDTO addDBDTO) throws Exception {


        //正则匹配只能是小写以及数字和_
        Pattern p = Pattern.compile("[a-z0-9_]*");
        Matcher m = p.matcher(addDBDTO.getDbName());

        if (!m.matches()) {
            throw new SearchMyException(SearchNewEnum.fail_2);
        }


        if (String.valueOf(addDBDTO.getDbName().charAt(0)).equals("_")) {
            throw new SearchMyException(SearchNewEnum.fail_2);
        }

        if (addDBDTO.getAppid() == null || addDBDTO.getAppid().equals("")) {
            throw new SearchMyException(SearchNewEnum.fail_16);
        }

        if (!isIndexExist(addDBDTO.getDbName())) {

            CreateIndexResponse response = client.admin().indices().prepareCreate(addDBDTO.getDbName()).get();
            client.admin().indices().prepareUpdateSettings(addDBDTO.getDbName()).setSettings(Settings.builder()
                    .put("index.mapper.dynamic", "false")).get();
            //表示是否成功
            iotLogger.info("【创建成功】--->" + addDBDTO.getDbName() + ":" + response.isAcknowledged());
        } else {
            throw new SearchMyException(SearchNewEnum.fail_1);
        }

        try {

            IndexInfo indexInfo = new IndexInfo();
            indexInfo.setName(addDBDTO.getDbName());
            indexInfo.setAppid(addDBDTO.getAppid());
            indexRepository.save(indexInfo);
        } catch (Exception e) {
            deleteService.delIndex(addDBDTO.getDbName());
            throw new SearchException(e.getMessage());
        }

    }


    //创建mapping（相同于创建表）
    public void CreateMappingsNew(AddTableDTO addTableDTO) {


        try {
            XContentBuilder builder = getMappingNew(addTableDTO);
            iotLogger.info("【创建格式为】：" + builder.string());
            PutMappingRequest mappingRequest = Requests.putMappingRequest(addTableDTO.getDbName()).source(builder).type(addTableDTO.getTableName());
            client.admin().indices().putMapping(mappingRequest).actionGet();

        } catch (SearchMyException e) {
            throw new SearchMyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            if (e.getMessage().contains("mapper") && e.getMessage().contains("changed") || (e.getMessage().contains("Mapper") && e.getMessage().contains("existing"))) {
                String message = e.getMessage().substring(e.getMessage().indexOf("[") + 1, e.getMessage().indexOf("]"));
                throw new SearchMyException(SearchNewEnum.fail_17, message);
            }
            throw new SearchException(e.getMessage());
        }
    }


    private XContentBuilder getMappingNew(AddTableDTO addTableDTO) throws Exception {

        XContentBuilder mapping = XContentFactory.jsonBuilder();
        //开始---
        mapping.startObject();

        // 字段名称集合, 不能有重复字段名称
        List<String> fieldNameList = new ArrayList<>();

        //1-判断fieldList是否为空
        if (addTableDTO.getFieldInfo() == null || addTableDTO.getFieldInfo().size() == 0) {
            throw new SearchMyException(SearchNewEnum.fail_3);
        }

        //2-遍历数据
        mapping.startObject("properties");
        for (FieldNewDTO field : addTableDTO.getFieldInfo()) {

            if (field.getFieldName().length() > 30) {
                throw new SearchMyException(SearchNewEnum.fail_4);
            }

            if (fieldNameList.contains(field.getFieldName())) {
                throw new SearchMyException(SearchNewEnum.fail_5);
            }

            //3-判断是否为date类型
            mapping.startObject(field.getFieldName());
            if (field.getFieldType() != null) {
                mapping.field("type", field.getFieldType());

                if (field.getFieldType().equals("date")) {
                    mapping.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
                }
            } else {
                throw new SearchMyException(SearchNewEnum.fail_6);
            }


            //4-分词判断
            if (field.getAnalyzer() != null) {
                if (!field.getAnalyzer().equals("ik")) {
                    throw new SearchMyException(SearchNewEnum.fail_7);
                }
                if (field.getFieldType().equals("text")) {
                    mapping.field("analyzer", "ik_max_word");
                    mapping.field("search_analyzer", "ik_max_word");
                }
            }
            //结束---
            mapping.endObject();
            fieldNameList.add(field.getFieldName());

        }

        mapping.endObject();
        mapping.field("dynamic", "strict");
        mapping.endObject();
        return mapping;
    }

}



