package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.WorkersEntity;

import java.util.Map;

/**
 * 抢修工人
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
public interface WorkersService extends IService<WorkersEntity> {

    PageUtils queryPage(Map<String, Object> params);
    WorkersEntity getByData(Map<String, Object> params);
    Map<String,Object> save_limit(Map<String, Object> params);
    Map<String,Object> update_limit(Map<String, Object> params);
}

