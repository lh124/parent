package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



/**
 * 计划针剂间隔表
 * @作者：邓召仕
 * 下午5:57:24
 */

@Getter
@Setter
public class TRuleIntervalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//接种程序间隔表主键
	private String id;
	//需间隔剂次规划字典表ID
	private String sourceClassId;
	//需间隔剂次
	private Integer sourceTimes;
	//与谁间隔的目标剂次字典表ID（参照表classid）
	private String targetClassId;
	//需间隔目标剂次，0代表所有剂次
	private Integer targetTimes;
	//间隔自然月
	private Integer intervalMonth;
	//间隔自然日
	private Integer intervalDay;



}
