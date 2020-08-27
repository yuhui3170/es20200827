package com.dianzi.es.rep.vo;

import com.dianzi.common.valid.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVo {

    private Long resourceId;
    /**
     * 资源名称
     */

    private String resourceName;
    /**
     * 资源地址
     */
    private String resourceAddr;
    /**
     * 资源联系方式
     */
    private String resourcePhone;
    /**
     * 资源备注
     */
    private String remark;
}
