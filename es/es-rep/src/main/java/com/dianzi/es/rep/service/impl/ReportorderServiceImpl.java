package com.dianzi.es.rep.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.rep.dao.ReportorderDao;
import com.dianzi.es.rep.entity.ReportorderEntity;
import com.dianzi.es.rep.service.ReportorderService;


@Service("reportorderService")
public class ReportorderServiceImpl extends ServiceImpl<ReportorderDao, ReportorderEntity> implements ReportorderService {
    @Autowired
    private ReportorderDao reportorderDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReportorderEntity> page = this.page(
                new Query<ReportorderEntity>().getPage(params),
                new QueryWrapper<ReportorderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void insert(Map<String, Object> params) {
        //数据处理
        params.put("reporterOrder",getOrderIdByUUId());      //16位报修单号
        System.out.println(params.get("reporterOrder"));
        reportorderDao.insert(params);
    }

    public static String getOrderIdByUUId() {
        int first = new Random(10).nextInt(8) + 1;
        System.out.println(first);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }

}