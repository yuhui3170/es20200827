package com.dianzi.es.rep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 事故标签
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@Data
@TableName("rep_labels")
public class LabelsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@TableId
	private Long labelId;
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
	 * 备注
	 */
	private String remark;

}
