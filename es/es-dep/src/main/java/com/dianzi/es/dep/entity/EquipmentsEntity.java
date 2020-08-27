package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备资源表
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_equipments")
public class EquipmentsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备id
	 */
	@TableId
	private Long equipId;
	/**
	 * 仓库id
	 */
	private Long storeId;
	/**
	 * 分类id
	 */
	private Integer typeId;
	/**
	 * 设备名称
	 */
	private String equipName;
	/**
	 * 设备总数量
	 */
	private Integer equipTotalmount;
	/**
	 * 设备当前数量
	 */
	private Integer equipCurrentmount;
	/**
	 * 设备单位
	 */
	private String equipUnit;
	/**
	 * 备注
	 */
	private String remark;

}
