package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 区域信息
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_area")
public class AreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@TableId
	private Long areaId;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 上级区域id
	 */
	private Long parentId;
	/**
	 * 区域等级 0-省 1-市 2-县/区
	 */
	private Integer areaLevel;
	/**
	 * 显示0 不显示1
	 */
	@TableLogic(value = "0",delval = "1")
	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;

	@TableField(exist = false)
	private List<AreaEntity> childArea;

}
