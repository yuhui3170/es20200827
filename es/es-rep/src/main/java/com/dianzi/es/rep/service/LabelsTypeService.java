package com.dianzi.es.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.rep.entity.LabelsTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-14 15:03:22
 */
public interface LabelsTypeService extends IService<LabelsTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

