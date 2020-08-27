package com.dianzi.es.dep.dao;

import com.dianzi.es.dep.entity.WorkersCodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-10 08:50:03
 */
@Mapper
public interface WorkersCodeDao extends BaseMapper<WorkersCodeEntity> {
    WorkersCodeEntity getByMobile(Map<String,Object> params);
    void saveCode(Map<String,Object> params);
}
