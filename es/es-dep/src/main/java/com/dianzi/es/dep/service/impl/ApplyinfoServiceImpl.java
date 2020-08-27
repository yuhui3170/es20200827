package com.dianzi.es.dep.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.ApplyinfoDao;
import com.dianzi.es.dep.entity.ApplyinfoEntity;
import com.dianzi.es.dep.service.ApplyinfoService;


@Service("applyinfoService")
public class ApplyinfoServiceImpl extends ServiceImpl<ApplyinfoDao, ApplyinfoEntity> implements ApplyinfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ApplyinfoEntity> page = this.page(
                new Query<ApplyinfoEntity>().getPage(params),
                new QueryWrapper<ApplyinfoEntity>()
        );

        return new PageUtils(page);
    }

}