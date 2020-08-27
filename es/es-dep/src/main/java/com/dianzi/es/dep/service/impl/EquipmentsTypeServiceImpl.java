package com.dianzi.es.dep.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.EquipmentsTypeDao;
import com.dianzi.es.dep.entity.EquipmentsTypeEntity;
import com.dianzi.es.dep.service.EquipmentsTypeService;


@Service("equipmentsTypeService")
public class EquipmentsTypeServiceImpl extends ServiceImpl<EquipmentsTypeDao, EquipmentsTypeEntity> implements EquipmentsTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeName = (String) params.get("typeName");
        String typeRemark = (String) params.get("typeRemark");
        IPage<EquipmentsTypeEntity> page = this.page(
                new Query<EquipmentsTypeEntity>().getPage(params),
                new QueryWrapper<EquipmentsTypeEntity>()
                .like(StringUtils.isNotBlank(typeName),"type_name",typeName)
                .or()
                .like(StringUtils.isNotBlank(typeRemark),"type_remark",typeRemark)
        );

        return new PageUtils(page);
    }

}