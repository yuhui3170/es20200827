package com.dianzi.es.rep.feign;

import com.dianzi.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/14
 */

@FeignClient("es-dep")
public interface ResourceFeignService {

    @RequestMapping("/dep/resource/ResourcesGet")
    public R ResourcesGet();
}
