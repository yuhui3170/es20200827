package com.dianzi.es.rep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 抢修单
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@Data
@TableName("rep_urgentorder")
public class UrgentorderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
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
