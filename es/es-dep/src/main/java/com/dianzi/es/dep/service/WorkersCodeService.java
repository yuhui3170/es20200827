package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.WorkersCodeEntity;

import java.util.Map;

/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-10 08:50:03
 */
public interface WorkersCodeService extends IService<WorkersCodeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    Map<String,Object> checkCode(Map<String, Object> params);
    Map<String,Object> sendCode(Map<String, Object> params);
}

