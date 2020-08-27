package com.dianzi.es.dep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.dianzi.common.valid.AddGroup;
import com.dianzi.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 部门资源
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@Data
@TableName("dep_resource")
public class ResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源id
	 */
	@Null(message = "修改必须指定Id",groups = {UpdateGroup.class})
	@NotNull(message = "新增不能指定Id",groups = {AddGroup.class})
	@TableId
	private Long resourceId;
	/**
	 * 资源名称
	 */

	@NotBlank(message = "供电资源名称不能为空",groups = {AddGroup.class})
	private String resourceName;
	/**
	 * 资源地址
	 */
	private String resourceAddr;
	/**
	 * 资源联系方式
	 */
	private String resourcePhone;
	/**
	 * 资源备注
	 */
	private String remark;

}
