package com.dianzi.es.rep.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 客户报修单
 * 
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@Data
@TableName("rep_reportorder")
public class ReportorderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 报修单编号
	 */
	private String reporterOrder;
	/**
	 * 报修人姓名
	 */
	private String reporterName;
	/**
	 * 报修人联系方式
	 */
	private String reporterPhone;
	/**
	 * 报修时间
	 */
	private Date reporterTime;
	/**
	 * 报修地点
	 */
	private String reporterAddr;
	/**
	 * 事故发生地点
	 */
	private String accidentAddr;
	/**
	 * 事故问题标签
	 */
	private String accidentLabel;
	/**
	 * 事故描述内容
	 */
	private String accidentContent;
	/**
	 * 事故参考图片
	 */
	private String accidentPic;
	/**
	 * 智能推荐抢修资源单位
	 */
	private Long resourceRecommend;
	/**
	 * 报修单备注
	 */
	private String remark;

}
