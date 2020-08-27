package com.dianzi.es.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dianzi.common.utils.R;
import com.dianzi.es.search.feign.UrgentOrderFeignService;
import com.dianzi.es.search.po.UrgentOrders;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dianzi.es.search.config.ESConst.ES_INDEX;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/12
 */

@RestController
@RequestMapping("es/search")
public class UrgentOrderController {

    @Autowired
    UrgentOrderFeignService urgentOrderFeignService;

    @Autowired
    private RestHighLevelClient esRestClient;


    /**
     * 创建索引
     */
    public boolean esindexCreate(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse =
                esRestClient.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    /**
     * 获取索引
     */
    public GetIndexRequest esindexGet(String indexName) {
        return new GetIndexRequest(indexName);
    }

    /**
     * 判断索引存在
     */
    public boolean isExistsIndex(String indexName) throws IOException {
        return esRestClient.indices().exists(esindexGet(indexName), RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     */

    public boolean esindexDel(String indexName) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete =
                esRestClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    /**
     * 增加索引数据
     */
    public void esindexUpdate(String indexName) throws IOException {
        if (isExistsIndex(indexName)) {
            //R urgentorders = urgentOrderFeignService.urgentorders();
            R urgentInfo = urgentOrderFeignService.esUrgentInfo();

            String data = JSON.toJSONString(urgentInfo.get("data"));
            System.out.println(data);

            List<UrgentOrders> list = JSONObject.parseArray(data, UrgentOrders.class);
            BulkRequest bulkRequest = new BulkRequest();

            for (UrgentOrders order : list) {
                bulkRequest.add(
                        new IndexRequest(indexName)
                                .id(order.getId().toString())
                                .source(JSON.toJSONString(order) , XContentType.JSON)
                );
            }

            BulkResponse bulkResponse = esRestClient.bulk(bulkRequest, RequestOptions.DEFAULT);

            System.out.println("status:======="+ bulkResponse.status().toString());

        } else {
            esindexCreate(indexName);
            esindexUpdate(indexName);
        }

    }

    /*
    * 查询数据
    * */

    public void Search(String indexName,String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("accidentReason", keyword);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(10, TimeUnit.SECONDS));
        sourceBuilder.size(100);

        searchRequest.source(sourceBuilder);
        SearchResponse response = esRestClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    public HashMap<String,Object> recommendationGet(String indexName,String addr,String reason) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);

        //SearchSourceBuilder sourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //构建DSL查询条件
        //2个must条件@地址和@故障标签
        TermQueryBuilder accidentAddr = QueryBuilders.termQuery("accidentAddr.keyword", addr);
        TermQueryBuilder accidentReason = QueryBuilders.termQuery("accidentReason.keyword", reason);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(accidentAddr).must(accidentReason);
        sourceBuilder.query(boolQueryBuilder);

        //维修人员聚合
        TermsAggregationBuilder workerAggbuilder =
                AggregationBuilders.terms("workerAgg").field("workerIds.keyword").size(1);

        //资源id聚合
        TermsAggregationBuilder resAggbuilder =
                AggregationBuilders.terms("resAgg").field("resourceId").size(1).subAggregation(workerAggbuilder);


        sourceBuilder.aggregation(resAggbuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = esRestClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms resAgg = aggregations.get("resAgg");

        HashMap<String, Object> resMap = new HashMap<>();

        for (Terms.Bucket resBucket : resAgg.getBuckets()) {
            resMap.put("resourceId",resBucket.getKeyAsString());
            resMap.put("count",String.valueOf(resBucket.getDocCount()));
            Terms workerAgg = resBucket.getAggregations().get("workerAgg");

            for (Terms.Bucket workerAggBucket : workerAgg.getBuckets()) {

                HashMap<String, String> workMap = new HashMap<>();
                workMap.put("workersId",workerAggBucket.getKeyAsString());
                workMap.put("count",String.valueOf(workerAggBucket.getDocCount()));
                resMap.put("workers", workMap);

            }

        }

        //System.out.println(resMap);

        return resMap;

        //System.out.println("*************++"+searchResponse.toString());


    }

    /**
     * 初始化
     */
    @RequestMapping("/init")
    public R init() throws IOException {
        String indexName = ES_INDEX;
        String reason = "设备老化";

        esindexDel(indexName);

        if(!isExistsIndex(indexName)){
            esindexCreate(indexName);
        }

        esindexUpdate(indexName);

        return R.ok();
    }

    @RequestMapping("/list")
    public R list() throws IOException {
        String indexName = ES_INDEX;
        String reason = "设备老化";

        Search(indexName,reason);

        return R.ok();
    }


    @RequestMapping("/search/{addr}/{reason}")
    public R searchBykeyword(@PathVariable("addr") String addr,@PathVariable("reason") String reason) throws IOException {
        String indexName = ES_INDEX;

        HashMap<String, Object> data = recommendationGet(indexName, addr, reason);

        return R.ok().put("data",data);
    }


    //AnalysisAPI

//    @RequestMapping("/analysis/urgentData")
//    public R urgentDataApiGet(){
//
//
//        return  R.ok().put("data",data);
//    }
}
