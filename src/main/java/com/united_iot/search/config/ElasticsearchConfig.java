package com.united_iot.search.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @auther jiahaowei
 * @date： 2017/11/9 0009
 * @time： 14:03
 * @project_name： mc_elasticsearch
 * @Description ：
 */
@Configuration
public class ElasticsearchConfig {

    @Value(value = "${ES-address}")
    String ESaddress;

    @Value(value = "${ES-cluster}")
    String EScluster;

    @Value(value = "${ES-port}")
    Integer ESport;

    @Bean
    public TransportClient client() throws UnknownHostException {

        InetSocketTransportAddress node_master = new InetSocketTransportAddress(
                InetAddress.getByName(ESaddress), ESport);
      /*  InetSocketTransportAddress node_savle1 = new InetSocketTransportAddress(
                InetAddress.getByName("localhost2"), 9300
        );
        InetSocketTransportAddress node_savle2 = new InetSocketTransportAddress(
                InetAddress.getByName("localhost3"), 9300
        );*/

        Settings settings = Settings.builder()
                //增加自动嗅探配置
                //.put("client.transport.sniff", true)
                .put("cluster.name", EScluster).build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node_master);
        //client.addTransportAddress(node_savle1);
        //client.addTransportAddress(node_savle2);


        return client;
    }
}
