package com.dianzi.es.dep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.dep.entity.ApplyinfoEntity;

import java.util.Map;

/**
 * 设备申请表
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
public interface ApplyinfoService extends IService<ApplyinfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

