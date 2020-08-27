package com.dianzi.es.dep.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.ResourceDao;
import com.dianzi.es.dep.entity.ResourceEntity;
import com.dianzi.es.dep.service.ResourceService;


@Service("resourceService")
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, ResourceEntity> implements ResourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String resourceName = (String)params.get("resourceName");
        String resourcePhone = (String)params.get("resourcePhone");
        IPage<ResourceEntity> page = this.page(
                new Query<ResourceEntity>().getPage(params),
                new QueryWrapper<ResourceEntity>()
                        .like(StringUtils.isNotBlank(resourceName),"resource_name", resourceName)
                        .or()
                        .like(StringUtils.isNotBlank(resourcePhone),"resource_phone", resourcePhone)
        );

        return new PageUtils(page);
    }

}