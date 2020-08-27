package com.dianzi.es.rep.service;

import com.dianzi.es.rep.dao.LableOrderAnaDao;
import com.dianzi.es.rep.vo.LabelOrderAnaVo;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/15
 */
public interface LableOrderAnaService extends LableOrderAnaDao {

    public List<LabelOrderAnaVo> GetLabelAna();
}
