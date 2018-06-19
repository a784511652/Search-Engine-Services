package com.united_iot.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication()
@MapperScan("com.united_iot.search.dataobject.mapper")
//@EnableEurekaClient
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
