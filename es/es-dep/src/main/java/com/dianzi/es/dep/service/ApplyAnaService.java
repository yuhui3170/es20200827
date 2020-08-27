package com.dianzi.es.dep.service;

import com.dianzi.common.vo.BaseInfo;
import com.dianzi.es.dep.dao.ApplyAnaDao;
import com.dianzi.es.dep.vo.ApplyAnaVo;
import com.dianzi.es.dep.vo.ApplyCount;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */
public interface ApplyAnaService extends ApplyAnaDao {

    List<ApplyAnaVo> getBaseApplyInfo(List<ApplyCount> info);
}
