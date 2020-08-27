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
 * @date 2020-08-14 10:46:00
 */
@Data
@TableName("dep_equipments_type")
public class EquipmentsTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类ID
	 */
	@TableId
	private Integer typeId;
	/**
	 * 分类名称
	 */
	private String typeName;
	/**
	 * 描述
	 */
	private String typeRemark;

}
