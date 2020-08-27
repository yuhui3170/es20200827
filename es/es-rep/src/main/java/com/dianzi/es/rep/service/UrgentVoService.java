package com.dianzi.es.rep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.es.rep.dao.UrgentVoDao;
import com.dianzi.es.rep.vo.UrgentAnaVo;
import com.dianzi.es.rep.vo.UrgentVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/13
 */
public interface UrgentVoService extends UrgentVoDao {
    List<UrgentVo> findAll();

    List<UrgentAnaVo> UrgentAnaList();
}
