package com.dianzi.es.search;

import com.alibaba.fastjson.JSON;
import com.dianzi.es.search.config.ElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() {

        System.out.println(client);
    }

    @Test
    public void indextest() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");

        indexRequest.id("1");

        person person = new person();
        person.setName("zhangsan");
        person.setAge(20);

        String s = JSON.toJSONString(person);
        indexRequest.source(s, XContentType.JSON);

        IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

    @Test
    public void SearchTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("users");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("name","zhangsan"));

        searchRequest.source(builder);

        SearchResponse search = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(search.toString());


    }


    @Data
    class person{
        private String name;
        private Integer age;
    }

}
