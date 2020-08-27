package com.dianzi.es.rep.vo;

import com.dianzi.common.vo.MonthOrders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrgentAnaVo {
    private long resourceID;

    private String resourceName;

    private List<MonthOrders> orderinfo;

    public List<MonthOrders> getOrderinfo() {
        return orderinfo;
    }

    public void setOrderinfo(List<MonthOrders> orderinfo) {
        this.orderinfo = orderinfo;
    }
}
