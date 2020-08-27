package com.dianzi.es.rep.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.rep.dao.LabelsDao;
import com.dianzi.es.rep.entity.LabelsEntity;
import com.dianzi.es.rep.service.LabelsService;


@Service("labelsService")
public class LabelsServiceImpl extends ServiceImpl<LabelsDao, LabelsEntity> implements LabelsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeId = (String) params.get("typeId");
        String labelName = (String) params.get("labelName");
        String labelDetail = (String) params.get("labelDetail");
        IPage<LabelsEntity> page = this.page(
                new Query<LabelsEntity>().getPage(params),
                new QueryWrapper<LabelsEntity>()
                .like(StringUtils.isNotBlank(labelName),"label_name",labelName)
                .eq(typeId!=null,"type_id",typeId)
                .or()
                .like(StringUtils.isNotBlank(labelDetail),"label_detail",labelDetail)
                .eq(typeId!=null,"type_id",typeId)
        );

        return new PageUtils(page);
    }

}