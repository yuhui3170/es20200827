package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.EquipmentsTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-14 10:46:00
 */
public interface EquipmentsTypeService extends IService<EquipmentsTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

