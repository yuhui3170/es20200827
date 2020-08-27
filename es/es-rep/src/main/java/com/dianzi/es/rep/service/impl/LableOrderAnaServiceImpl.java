package com.dianzi.es.rep.service.impl;

import com.dianzi.common.utils.AnaUtils;
import com.dianzi.common.vo.MonthOrders;
import com.dianzi.es.rep.dao.LableOrderAnaDao;
import com.dianzi.es.rep.service.LableOrderAnaService;
import com.dianzi.es.rep.vo.LabelOrderAnaVo;
import com.dianzi.es.rep.vo.LabelOrdersByMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/15
 */

@Service("LableOrderAnaService")
public class LableOrderAnaServiceImpl implements LableOrderAnaService {

    @Autowired
    private LableOrderAnaDao lableOrderAnaDao;

    @Override
    public List<LabelOrdersByMonth> GetOrdersByTypesMonths() {
        List<LabelOrdersByMonth> labelOrdersByMonths = lableOrderAnaDao.GetOrdersByTypesMonths();
        return labelOrdersByMonths;
    }


    @Override
    public List<LabelOrderAnaVo> GetLabelAna() {

        List<LabelOrderAnaVo> labelOrderAnaVos = new ArrayList<LabelOrderAnaVo>(){};

        List<LabelOrdersByMonth> labelOrdersByMonths = lableOrderAnaDao.GetOrdersByTypesMonths();

        String typeName = "";

        for (LabelOrdersByMonth labelOrdersByMonth : labelOrdersByMonths) {
            if (!typeName.equals(labelOrdersByMonth.getTypeName())) {
                LabelOrderAnaVo labelOrderAnaVo = new LabelOrderAnaVo();
                typeName = labelOrdersByMonth.getTypeName();
                labelOrderAnaVo.setTypeName(labelOrdersByMonth.getTypeName());
                labelOrderAnaVos.add(labelOrderAnaVo);
            }
        }


        for (LabelOrderAnaVo labelOrderAnaVo : labelOrderAnaVos){
            List<MonthOrders> monthOrdersList = new ArrayList<MonthOrders>();
            for (LabelOrdersByMonth labelOrdersByMonth : labelOrdersByMonths) {

                if(labelOrderAnaVo.getTypeName().equals(labelOrdersByMonth.getTypeName())) {

                    MonthOrders monthOrders = new MonthOrders();
                    monthOrders.setMon(labelOrdersByMonth.getMon());
                    monthOrders.setOrders(labelOrdersByMonth.getOrders());

                    monthOrdersList.add(monthOrders);
                }
            }

            List<MonthOrders> monthOrders = AnaUtils.MonthListUpdate(monthOrdersList);

            labelOrderAnaVo.setOrderinfo(monthOrders);
        }


        return labelOrderAnaVos;
    }


}
