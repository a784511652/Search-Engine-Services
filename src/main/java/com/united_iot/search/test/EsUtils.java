package com.united_iot.search.test;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class EsUtils {
	
	private static Client client;
	
	public EsUtils(){
    	Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();

    	try {
			client = new PreBuiltTransportClient(settings)
			                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.12.104"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
	}
	
	/*
	 * 创建索引
	 */
	public static void createIndex(String indexDbName, String type, String id, String index){


    	//创建索引库,索引库名:blog,类型：tweet,id:1
    	IndexResponse indexResponse = client.prepareIndex(indexDbName, type, id)
    			.setSource(index).get();
    	System.out.println(indexResponse.status());
    	if((indexResponse.status() == RestStatus.OK) || (indexResponse.status() == RestStatus.CREATED)){
    		System.out.println("成功");
    	}else{
    		System.out.println("创建失败");
    	}
	}
	/*
	 * 判断索引是否存在
	 */
	public static boolean indexExists(String indexDbName, String type, String id){
		//搜索数据
        GetResponse getResponse = client.prepareGet(indexDbName, type, id).execute().actionGet();
    	//输出结果
        //System.out.println(getResponse.getSourceAsString());
        return getResponse.isExists();
	}
	
	/*
	 * 搜索
	 */
	
	public static void search(String indexDbName, String type, String field,  String keyWord){
		//分页，每页10条，显示第一页
    	SearchRequestBuilder requestBuilder = client.prepareSearch(indexDbName).setTypes(type).setFrom(0).setSize(10);
    	
    	SearchResponse searchResponse = requestBuilder.setQuery(QueryBuilders.matchPhraseQuery(field, keyWord))
    			.execute().actionGet();
    	//System.out.println("  " + searchResponse); 
    	
    	SearchHits hits = searchResponse.getHits();
    	if(hits.totalHits()>0){
    		for(SearchHit hit: hits){
    			System.out.println("Core:" + hit.getScore() + ";   " + "Content:" + hit.getSourceAsString());
    		}
    	}else{
    		System.err.println("搜到0条记录");
    	}
	}
	
	/*
	 * 删除索引
	 */
	
	public static boolean del(String index, String type, String id){
		//删除整个索引库
//		DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index)
//				.execute().actionGet();
//		return deleteIndexResponse.isAcknowledged();
		
		//通过id删除
		DeleteResponse deleteResponse = client.prepareDelete(index, type, id).execute().actionGet();
		System.out.println(deleteResponse.status());
		if(deleteResponse.status() == RestStatus.OK){
			System.out.println("删除ID成功");
			return true;
		}else{
			System.out.println("删除ID失败");
			return false;
		}
		
	}
	/*
	 *删除索引库 
	 */
	public static boolean delIndexDb(String indexDbName){
		DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(indexDbName)
				.execute().actionGet();
		return deleteIndexResponse.isAcknowledged();
	}
	
/***************************Mapping*********************/
	public static XContentBuilder getMapping(){
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder()
					.startObject()
//							.startObject("_timestamp")
//								.field("store", "true")
//								.field("enabled", true) 
//							.endObject()
							.startObject("properties")
									.startObject("timestamp")
											.field("type", "date")
									.endObject()
									
									.startObject("topic")
											.field("type", "string")
									.endObject()
									
									.startObject("author")
										.field("type", "string")
										.field("index", "not_analyzed")
									.endObject()
									
									.startObject("reading")
										.field("type", "integer")
										.field("index", "not_analyzed")
									.endObject()
									
									.startObject("url")
										.field("type", "string")
										.field("index", "not_analyzed")
									.endObject()
									
									.startObject("content")
										.field("type", "string")
//										.field("index", "analyzed")
//										.field("analyzer", "ik")
									.endObject()
									
							.endObject()
						//.endObject()
					.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping;
	}
	
	public static boolean createMapping(String indexDbName, String type){

		//创建时需要indexDbName已经创建才行，否则会报错
		PutMappingRequest mappingRequest = Requests.putMappingRequest(indexDbName)
				.type(type).source(getMapping());
		PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mappingRequest).actionGet();
		return putMappingResponse.isAcknowledged();
	}
	
	public static boolean createIndexDB(String indexDbName){
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexDbName);
		CreateIndexResponse createIndexResponse = client.admin().indices().create(createIndexRequest).actionGet();
		return createIndexResponse.isAcknowledged();
	}
}
