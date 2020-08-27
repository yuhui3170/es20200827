package com.dianzi.es.dep.vo;

import com.dianzi.common.utils.AnaUtils;
import com.dianzi.common.vo.ApplyInfoBaseInfo;
import com.dianzi.common.vo.BaseInfo;
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
public class ApplyAnaVo {

    private String storeName;
    private List<ApplyInfoBaseInfo> applyInfo;
}
