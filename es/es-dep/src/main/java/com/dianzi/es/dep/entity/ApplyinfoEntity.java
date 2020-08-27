package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备申请表
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_applyinfo")
public class ApplyinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 设备id
	 */
	private Long equipId;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 工人id
	 */
	private String workerId;
	/**
	 * 工人名称
	 */
	private String workerName;
	/**
	 * 抢修单编号
	 */
	private String repairOrderid;
	/**
	 * 备注
	 */
	private String remark;

}
