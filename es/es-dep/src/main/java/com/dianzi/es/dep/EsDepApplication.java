package com.dianzi.es.dep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@MapperScan("com.dianzi.es.dep.dao")
@SpringBootApplication
public class EsDepApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsDepApplication.class, args);
    }

}
