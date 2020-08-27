package com.dianzi.es.dep.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.EquipmentsDao;
import com.dianzi.es.dep.entity.EquipmentsEntity;
import com.dianzi.es.dep.service.EquipmentsService;


@Service("equipmentsService")
public class EquipmentsServiceImpl extends ServiceImpl<EquipmentsDao, EquipmentsEntity> implements EquipmentsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String equipName = (String) params.get("equipName");
        String storeId = (String) params.get("storeId");
        String typeId = (String) params.get("typeId");
        IPage<EquipmentsEntity> page = this.page(
                new Query<EquipmentsEntity>().getPage(params),
                new QueryWrapper<EquipmentsEntity>()
                .like(StringUtils.isNotBlank(equipName),"equip_name",equipName)
                .eq(storeId != null,"store_id", storeId)
                .eq(typeId != null,"type_id", typeId)
        );
        System.out.println(page.getTotal());
        return new PageUtils(page);
    }

}