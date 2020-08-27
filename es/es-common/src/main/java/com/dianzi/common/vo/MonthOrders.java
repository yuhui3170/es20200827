package com.dianzi.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthOrders {
    private Integer orders;
    private String mon;

}
