package com.dianzi.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyInfoBaseInfo {
    private String equipTypeName;


   private List<BaseInfo> info;
}
