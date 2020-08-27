package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 抢修工人
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_workers")
public class WorkersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工人id
	 */
	@TableId
	private Long workerId;
	/**
	 * 资源id
	 */
	private Long resourceId;
	/**
	 * 工人姓名
	 */
	private String workerName;
	/**
	 * 工人出生日期
	 */
	private String workerBirth;
	/**
	 * 工人职位
	 */
	private String workerPosition;
	/**
	 * 接单数量
	 */
	private Long ordersReceived;
	/**
	 * 结单数量
	 */
	private Long ordersFinished;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 头像
	 */
	private String workPic;
	/**
	 * 昵称
	 */
	private String workNickname;
	/**
	 * 联系电话
	 */
	private String workMobile;
	/**
	 * 密码
	 */
	private String workPassword;

}
