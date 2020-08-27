package com.dianzi.es.dep.vo;

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
public class ApplyCount {
    private String equipName;
    private String storeName;
    private Integer counts;
    private String typeName;
}
