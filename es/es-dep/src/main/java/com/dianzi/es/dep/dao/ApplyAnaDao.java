package com.dianzi.es.dep.dao;

import com.dianzi.es.dep.vo.ApplyCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */

@Mapper
public interface ApplyAnaDao {
    List<ApplyCount> getInfo();
}
