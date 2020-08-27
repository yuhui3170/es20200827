package com.dianzi.es.search.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UrgentOrders {
    private Long id;
    /**
     * 报修单编号
     */
    private String reporterOrder;
    /**
     * 抢修单编号
     */
    private String urgentOrder;
    /**
     * 抢修单状态 0-已归档 1-审核中 2-审核不通过 3-待派单 4-已派单 5-抢修中
     */
    private Integer orderStatus;
    /**
     * 抢修资源id
     */

    private Integer resourceId;
    /**
     * 事故发生地
     */

    private String accidentAddr;
    /**
     * 抢修工人ids
     */

    private String workerIds;

    /**
     * 受理时间
     */

    private Date acceptTime;
    /**
     * 派单时间
     */
    private Date dispatchTime;
    /**
     * 到达现场时间
     */
    private Date arrivedTime;
    /**
     * 修复时间 -分钟
     */
    private Integer repairedTime;
    /**
     * 故障原因
     */
    private String accidentReason;
    /**
     * 报修内容
     */
    private String repairedContent;
    /**
     * 抢修工作评价
     */
    private String evaluateContent;
    /**
     * 备注
     */
    private String remark;
}
