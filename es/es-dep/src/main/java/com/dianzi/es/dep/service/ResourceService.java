package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.ResourceEntity;

import java.util.Map;

/**
 * 部门资源
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
public interface ResourceService extends IService<ResourceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

