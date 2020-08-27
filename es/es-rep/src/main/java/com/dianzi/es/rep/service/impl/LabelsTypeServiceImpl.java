package com.dianzi.es.rep.service.impl;

import com.dianzi.es.rep.util.TreeMenuFormat;
import com.dianzi.es.rep.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.rep.dao.LabelsTypeDao;
import com.dianzi.es.rep.entity.LabelsTypeEntity;
import com.dianzi.es.rep.service.LabelsTypeService;


@Service("labelsTypeService")
public class LabelsTypeServiceImpl extends ServiceImpl<LabelsTypeDao, LabelsTypeEntity> implements LabelsTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeName = (String) params.get("typeName");
        String typeRemark = (String) params.get("typeRemark");
        IPage<LabelsTypeEntity> page = this.page(
                new Query<LabelsTypeEntity>().getPage(params),
                new QueryWrapper<LabelsTypeEntity>()
                .like(StringUtils.isNotBlank(typeName),"type_name",typeName)
                .or()
                .like(StringUtils.isNotBlank(typeRemark),"type_remark",typeRemark)
        );

        return new PageUtils(page);
    }


}