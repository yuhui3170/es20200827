package com.dianzi.es.rep.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dianzi.common.utils.AnaUtils;
import com.dianzi.common.utils.R;
import com.dianzi.common.vo.MonthOrders;
import com.dianzi.es.rep.dao.UrgentVoDao;
import com.dianzi.es.rep.feign.ResourceFeignService;
import com.dianzi.es.rep.service.UrgentVoService;
import com.dianzi.es.rep.vo.ResourceVo;
import com.dianzi.es.rep.vo.UrgentAnaVo;
import com.dianzi.es.rep.vo.UrgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/13
 */

@Service("UrgentVoService")
public class UrgentVoServiceImpl implements UrgentVoService {

    @Autowired
    UrgentVoDao urgentVoDao;

    @Autowired
    private ResourceFeignService resourceFeignService;

    @Override
    public List<UrgentVo> findAll() {
        return urgentVoDao.findAll();
    }

    @Override
    public List<UrgentAnaVo> UrgentAnaList() {

        R resourcesGet = resourceFeignService.ResourcesGet();
        String jsonString = JSONObject.toJSONString(resourcesGet.get("data"));


        List<ResourceVo> resourceVos =
                JSONObject.parseArray(jsonString, ResourceVo.class);

        List<UrgentAnaVo> urgentAnaVosList = new ArrayList<UrgentAnaVo>() {
        };


        for (ResourceVo resourceVo : resourceVos) {

            UrgentAnaVo urgentAnaVo = new UrgentAnaVo();

            urgentAnaVo.setResourceID(resourceVo.getResourceId());
            urgentAnaVo.setResourceName(resourceVo.getResourceName());


            List<MonthOrders> monthOrders =
                    GetOrdersByMonths(String.valueOf(resourceVo.getResourceId()));


            urgentAnaVo.setOrderinfo(monthOrders);
            //System.out.println(urgentAnaVo.toString());

            urgentAnaVosList.add(urgentAnaVo);

        }

        return urgentAnaVosList;
    }

    @Override
    public List<com.dianzi.common.vo.MonthOrders> GetOrdersByMonths(String resourceId) {


        List<com.dianzi.common.vo.MonthOrders> ordersOrign = urgentVoDao.GetOrdersByMonths(resourceId);
        String[] last12Months = AnaUtils.getLast12Months();

        List<com.dianzi.common.vo.MonthOrders> ordersUpdated = new ArrayList<com.dianzi.common.vo.MonthOrders>();

        //获取过去十二个月的数据,数据数据某月不存在则0处理

        boolean flag = false;

        for (String month : last12Months) {

            flag = false;

            for (com.dianzi.common.vo.MonthOrders orderOrign : ordersOrign) {
                if (month.equals(orderOrign.getMon())) {
                    ordersUpdated.add(orderOrign);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                com.dianzi.common.vo.MonthOrders ordersTemp = new com.dianzi.common.vo.MonthOrders();
                ordersTemp.setMon(month);
                ordersTemp.setOrders(0);
                ordersUpdated.add(ordersTemp);
            }
        }

        return ordersUpdated;
    }

}
