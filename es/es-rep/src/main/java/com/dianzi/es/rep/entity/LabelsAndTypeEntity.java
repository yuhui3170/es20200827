package com.dianzi.es.rep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-14 15:03:22
 */
@Data
//@TableName("rep_labels_type")
public class LabelsAndTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 标签ID
	 */
	@TableId
	private Integer labelId;

	/**
	 * 标签名称
	 */
	private String labelName;

	/**
	 * 标签详情
	 */
	private String labelDetail;

	/**
	 * 分类ID
	 */
	@TableId
	private Integer typeId;
	/**
	 * 分类名称
	 */
	private String typeName;


}
