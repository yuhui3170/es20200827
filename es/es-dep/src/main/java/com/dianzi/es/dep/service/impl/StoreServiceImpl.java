package com.dianzi.es.dep.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.StoreDao;
import com.dianzi.es.dep.entity.StoreEntity;
import com.dianzi.es.dep.service.StoreService;


@Service("storeService")
public class StoreServiceImpl extends ServiceImpl<StoreDao, StoreEntity> implements StoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String storeName = (String) params.get("storeName");
        String resourceId = (String) params.get("resourceId");
        IPage<StoreEntity> page = this.page(
                new Query<StoreEntity>().getPage(params),
                new QueryWrapper<StoreEntity>()
                .like(StringUtils.isNotBlank(storeName),"store_name",storeName)
                .eq(resourceId != null,"resource_id", resourceId)
        );

        return new PageUtils(page);
    }

}