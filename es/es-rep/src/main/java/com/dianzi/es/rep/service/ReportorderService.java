package com.dianzi.es.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.rep.entity.ReportorderEntity;

import java.util.Map;

/**
 * 客户报修单
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
public interface ReportorderService extends IService<ReportorderEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void insert(Map<String, Object> params);
}

