package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资源所属仓库
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_store")
public class StoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库id
	 */
	@TableId
	private Long storeId;
	/**
	 * 资源id
	 */
	private Long resourceId;
	/**
	 * 仓库名称
	 */
	private String storeName;
	/**
	 * 仓库负责人
	 */
	private Integer storeManager;
	/**
	 * 仓库联系电话
	 */
	private String managerPhone;
	/**
	 * 备注
	 */
	private String remark;

}
