package com.dianzi.es.rep.vo;

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
public class LabelOrdersByMonth {
    private String typeName;

    private String mon;

    private Integer orders;
}
