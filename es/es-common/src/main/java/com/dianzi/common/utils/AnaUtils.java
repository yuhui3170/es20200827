package com.dianzi.common.utils;

import com.dianzi.common.vo.MonthOrders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */
public class AnaUtils {

    public static String[] getLast12Months() {

        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        //如果当前日期大于二月份的天数28天或者29天会导致计算月份错误，会多出一个三月份，故设置一个靠前日期解决此问题
        cal.set(Calendar.DAY_OF_MONTH, 1);

        String month = "";
        for (int i = 0; i < 12; i++) {
            month = ""+(cal.get(Calendar.MONTH) + 1);
            if( Integer.valueOf(month) < 10 ){
                month = "0".concat(month);
            }
            //last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
            last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + month;
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
        }

        return last12Months;
    }

    public static List<MonthOrders> MonthListUpdate(List<MonthOrders> ordersOrign){

        String[] last12Months = getLast12Months();
        List<MonthOrders> ordersUpdated = new ArrayList<MonthOrders>();
        //获取过去十二个月的数据,数据数据某月不存在则0处理
        boolean flag = false;

        for (String month : last12Months) {
            flag = false;
            for (MonthOrders orderOrign : ordersOrign) {
                if (month.equals(orderOrign.getMon())) {
                    ordersUpdated.add(orderOrign);
                    flag = true;
                    break;
                }
            }

            if(!flag){
                MonthOrders ordersTemp = new MonthOrders();
                ordersTemp.setMon(month);
                ordersTemp.setOrders(0);
                ordersUpdated.add(ordersTemp);
            }
        }


        return ordersUpdated;

    }
}
