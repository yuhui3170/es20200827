package com.dianzi.es.rep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages = "com.dianzi.es.rep.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class EsRepApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsRepApplication.class, args);
    }

}
