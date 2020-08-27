package com.dianzi.es.rep.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dianzi.common.vo.MonthOrders;
import com.dianzi.es.rep.vo.UrgentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/13
 */
@Mapper
public interface UrgentVoDao  {
    List<UrgentVo> findAll();

    List<MonthOrders> GetOrdersByMonths(String resourceId);

}
