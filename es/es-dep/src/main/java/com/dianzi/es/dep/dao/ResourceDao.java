package com.dianzi.es.dep.dao;

import com.dianzi.es.dep.entity.ResourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门资源
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Mapper
public interface ResourceDao extends BaseMapper<ResourceEntity> {
	
}
