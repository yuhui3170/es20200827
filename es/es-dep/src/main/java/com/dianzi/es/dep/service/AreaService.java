package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.AreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 区域信息
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
public interface AreaService extends IService<AreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AreaEntity> listWithTree();

    void removeAreaByIds(List<Long> asList);
}

