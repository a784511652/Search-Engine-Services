package com.united_iot.search.service.Impl;

import com.alibaba.fastjson.JSON;
import com.united_iot.search.DTO.NewDTO.AddDocumentDTO;
import com.united_iot.search.DTO.addDocument.AddDTO;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchDBException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.IndexService;
import com.united_iot.search.service.UpdateService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iemylife.iot.logging.IotLogger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:56
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private IndexService indexService;

    @Autowired
    private TransportClient client;

    @Autowired
    private IotLogger iotLogger;

    @Override
    public long AddDocument(AddDTO addDTO) throws Exception {
        //更新名称
        addDTO.setIndexName("pri:"+addDTO.getIndexName());

        if (!indexService.isIndexExist(addDTO.getIndexName())) {
            throw new MyException(ResultEnum.fail_8);
        }

        if (!indexService.isExistsType(addDTO.getIndexName(), addDTO.getType())) {
            throw new MyException(ResultEnum.fail_9);
        }

        //1--通过Url去获取mapping的数据
        List<String> keylist = new ArrayList<>();
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState()
                .execute().actionGet().getState().getMetaData().getIndices().get(addDTO.getIndexName()).getMappings();
        String url = mappings.get(addDTO.getType()).source().toString();
        String result_Type = JSON.parseObject(url).getString(addDTO.getType());
        String result_properties = JSON.parseObject(result_Type).getString("properties");


        //2--得到mapping的所有字段
        Gson gson = new Gson();
        Map<String, Object> keys = gson.fromJson(result_properties, new TypeToken<Map<String, Object>>() {
        }.getType());
        System.out.println(keys);
        keylist.addAll(keys.keySet());

        for (Object userkey : addDTO.getFieldData().keySet()) {
            if (!keylist.contains(userkey)) {
                throw new MyException(ResultEnum.fail_7);
            }
        }

        //3--向字段里添加数据
        XContentBuilder content = XContentFactory.jsonBuilder();
        content.startObject();
        for (Object key : addDTO.getFieldData().keySet()) {
            content.field(key.toString(), addDTO.getFieldData().get(key));
            iotLogger.debug("key:" + key.toString() + "  " + "value: " + addDTO.getFieldData().get(key));

        }
        content.endObject();

        IndexResponse resultt = client.prepareIndex(addDTO.getIndexName(), addDTO.getType())
                //放置文档
                .setSource(content)
                .setId(addDTO.getId())
                //获得结果
                .get();
        iotLogger.info("【数据插入成功】--> 文档id：" + resultt.getId() + "  数据版本号：" + resultt.getVersion());
        return resultt.getVersion();

    }

    //--------------------------------------new--------------------------------------------
    public void AddDocumentNew(AddDocumentDTO addDTO) throws Exception {
        if (!indexService.isIndexExist(addDTO.getDbName())) {
            throw new SearchMyException(SearchNewEnum.fail_8);
        }

        if (!indexService.isExistsType(addDTO.getDbName(), addDTO.getTbName())) {
            throw new SearchMyException(SearchNewEnum.fail_9);
        }

        if (addDTO.getId()==null||addDTO.getId().equals("")||addDTO.getId().contains(" ")){
            throw  new SearchDBException();
        }

        //1--通过Url去获取mapping的数据
        List<String> keylist = new ArrayList<>();
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState()
                .execute().actionGet().getState().getMetaData().getIndices().get(addDTO.getDbName()).getMappings();
        String url = mappings.get(addDTO.getTbName()).source().toString();
        String result_Type = JSON.parseObject(url).getString(addDTO.getTbName());
        String result_properties = JSON.parseObject(result_Type).getString("properties");


        //2--得到mapping的所有字段
        Gson gson = new Gson();
        Map<String, Object> keys = gson.fromJson(result_properties, new TypeToken<Map<String, Object>>() {
        }.getType());
        keylist.addAll(keys.keySet());

        for (Object userkey : addDTO.getDocData().keySet()) {
            if (!keylist.contains(userkey)) {
                throw new SearchMyException(SearchNewEnum.fail_7);
            }
        }

        //3--向字段里添加数据
        XContentBuilder content = XContentFactory.jsonBuilder();
        content.startObject();
        for (Object key : addDTO.getDocData().keySet()) {
            content.field(key.toString(), addDTO.getDocData().get(key));
            iotLogger.debug("key:" + key.toString() + "  " + "value: " + addDTO.getDocData().get(key));

        }
        content.endObject();

        IndexResponse resultt = client.prepareIndex(addDTO.getDbName(), addDTO.getTbName())
                //放置文档
                .setSource(content)
                .setId(addDTO.getId())
                //获得结果
                .get();
        iotLogger.info("【数据插入成功】--> 文档id：" + resultt.getId() + "  数据版本号：" + resultt.getVersion());

    }
}
