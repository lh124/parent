package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 每位儿童应接种计划保存，根据国家免疫规划表
 * 
 * @作者：邓召仕 下午5:59:04
 */

@Getter
@Setter
public class TRulePlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 儿童规划程序表主键
	private String id;
	// 儿童编码/唯一标识
	private String childId;
	// 疫苗类别标识，类别ID
	private String classId;
	// 接种剂次
	private Integer injectionTimes;
	// 接种时间，为null时表示没有接种
	private Date inoculateTime;
	// 最早接种时间，儿童需达到这时间才可接种该疫苗
	private Date firstTime;
	// 及时时间，达到最早时间，小于等于该时间为及时
	private Date timelyTime;
	// 合格时间
	private Date qualifiledTime;
	// 最后可接种时间
	private Date lastTime;
	// 接种状态：0、未接种，1、间短，2、及时，3、合格，4、超期
	private Integer state;
	// 接种或替代针剂，接种信息ID
	private String inoculateId;

}
