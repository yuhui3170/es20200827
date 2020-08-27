package com.dianzi.es.rep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.rep.dao.UrgentorderDao;
import com.dianzi.es.rep.entity.UrgentorderEntity;
import com.dianzi.es.rep.service.UrgentorderService;


@Service("urgentorderService")
public class UrgentorderServiceImpl extends ServiceImpl<UrgentorderDao, UrgentorderEntity> implements UrgentorderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UrgentorderEntity> page = this.page(
                new Query<UrgentorderEntity>().getPage(params),
                new QueryWrapper<UrgentorderEntity>()
        );

        return new PageUtils(page);
    }

}