package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-10 08:50:03
 */
@Data
@TableName("dep_workers_code")
public class WorkersCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 验证码ID
	 */
	@TableId
	private Integer cordId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 过期时间
	 */
	private Date expireTime;

}
