package com.dianzi.es.rep.dao;

import com.dianzi.es.rep.vo.LabelOrderAnaVo;
import com.dianzi.es.rep.vo.LabelOrdersByMonth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/15
 */

@Repository
@Mapper
public interface LableOrderAnaDao {

    List<LabelOrdersByMonth> GetOrdersByTypesMonths();
}
