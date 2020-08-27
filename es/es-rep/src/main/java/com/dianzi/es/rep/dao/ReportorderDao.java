package com.dianzi.es.rep.dao;

import com.dianzi.es.rep.entity.ReportorderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 客户报修单
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@Mapper
public interface ReportorderDao extends BaseMapper<ReportorderEntity> {
	void insert(Map<String, Object> params);
}
