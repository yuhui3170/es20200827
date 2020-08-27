package com.dianzi.es.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.rep.entity.UrgentorderEntity;

import java.util.Map;

/**
 * 抢修单
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
public interface UrgentorderService extends IService<UrgentorderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

