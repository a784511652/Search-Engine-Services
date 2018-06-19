package com.united_iot.search.service.Impl;

import com.united_iot.search.DTO.deleteDocument.DeleteDTO;
import com.united_iot.search.enumm.ResultEnum;
import com.united_iot.search.enumm.SearchNewEnum;
import com.united_iot.search.exception.MyException;
import com.united_iot.search.exception.SearchMyException;
import com.united_iot.search.service.DeleteService;
import com.united_iot.search.service.IndexService;
import com.iemylife.iot.logging.IotLogger;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @auther jiahaowei
 * @date： 2017/11/24 0024
 * @time： 8:54
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private IndexService indexService;

    @Autowired
    private TransportClient client;

    @Autowired
    private IotLogger iotLogger;

    //根据ID删除文档
    public void deleteDocument(DeleteDTO deleteDTO) throws Exception {

        //更新名称
        deleteDTO.setIndexName("pri:"+deleteDTO.getIndexName());

        System.out.println(deleteDTO.getIndexName());
        if (!indexService.isIndexExist(deleteDTO.getIndexName())) {
            throw new MyException(ResultEnum.fail_8);
        }
        DeleteResponse result = client.prepareDelete(deleteDTO.getIndexName(), deleteDTO.getType(), deleteDTO.getId())
                .get();

        if (!indexService.isExistsType(deleteDTO.getIndexName(), deleteDTO.getType())) {
            throw new MyException(ResultEnum.fail_9);
        }

        if (!result.getResult().toString().equals("DELETED")) {
            throw new MyException(ResultEnum.fail_10);

        }
        iotLogger.info("【删除成功】:" + result.getResult().toString());
    }


    //删除单个索引
    public void delIndex(String IndexName) throws Exception {
        if (indexService.isIndexExist(IndexName)) {
            DeleteIndexResponse response = client.admin().indices().prepareDelete(IndexName).get();
            iotLogger.info("【删除成功】:" + IndexName + ":" + response.isAcknowledged());
        } else {
            throw new MyException(ResultEnum.fail_8);
        }
    }

    //根据条件删除记录
    public void deleteByQuery(DeleteDTO deleteDTO) throws Exception {
        if (indexService.isIndexExist(deleteDTO.getIndexName())) {
            for (Map.Entry<String, String> entry : deleteDTO.getFieldData().entrySet()) {
                BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchQuery(entry.getKey(), entry.getValue())).source(deleteDTO.getIndexName()).get();
                iotLogger.info("【删除成功】：删除了" + String.valueOf(response.getDeleted()) + "条数据");
            }
        } else {
            throw new MyException(ResultEnum.fail_8);
        }

    }

    //删除所有搜索索引
    public void deleteAllIndex() {
        ClusterStateResponse response = client.admin().cluster().prepareState().get();
        // 获取所有索引
        String[] indexs = response.getState().getMetaData().getConcreteAllIndices();
        for (String index : indexs) {
            // 清空所有索引。
            DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index).execute()
                    .actionGet();
            if (deleteIndexResponse.isAcknowledged()) {
                iotLogger.info("【删除成功】" + index + " delete");
            }

        }
    }


    //---------------------------------------------new ----------------------------------------------

    //根据ID删除文档
    public void deleteDocumentNew(DeleteDTO deleteDTO) throws Exception {


        if (!indexService.isIndexExist(deleteDTO.getIndexName())) {
            throw new SearchMyException(SearchNewEnum.fail_8);
        }
        DeleteResponse result = client.prepareDelete(deleteDTO.getIndexName(), deleteDTO.getType(), deleteDTO.getId())
                .get();

        if (!indexService.isExistsType(deleteDTO.getIndexName(), deleteDTO.getType())) {
            throw new SearchMyException(SearchNewEnum.fail_9);
        }

        if (!result.getResult().toString().equals("DELETED")) {
            throw new SearchMyException(SearchNewEnum.fail_10);

        }
        iotLogger.info("【删除成功】:" + result.getResult().toString());
    }
}
