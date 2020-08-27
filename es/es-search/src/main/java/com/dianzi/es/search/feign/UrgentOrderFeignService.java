package com.dianzi.es.search.feign;

import com.dianzi.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/12
 */


@FeignClient("es-rep")
public interface UrgentOrderFeignService {
    @RequestMapping("/rep/urgentorder/feign/list")
    public R urgentorders();

    @RequestMapping("/rep/urgentorder/feign/all")
    public R esUrgentInfo();
}
