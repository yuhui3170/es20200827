package com.dianzi.es.dep.dao;

import com.dianzi.es.dep.entity.WorkersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 抢修工人
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Mapper
public interface WorkersDao extends BaseMapper<WorkersEntity> {
    WorkersEntity getByData(Map<String, Object> params);
    void updateByMobile(Map<String, Object> params);
    void updateMobileByMobile(Map<String, Object> params);
    void save_limit(Map<String, Object> params);
    void update_limit(Map<String, Object> params);
}
