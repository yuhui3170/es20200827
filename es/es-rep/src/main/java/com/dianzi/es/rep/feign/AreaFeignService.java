package com.dianzi.es.rep.feign;

import com.dianzi.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/7/9
 */

@FeignClient("es-dep")
public interface AreaFeignService {
    @RequestMapping("/dep/area/rep/list")
    public R repArea();
}
