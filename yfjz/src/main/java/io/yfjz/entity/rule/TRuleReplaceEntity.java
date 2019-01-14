package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 接种规划映射关系表
 * 
 * @作者：邓召仕 下午5:59:55
 */

@Getter
@Setter
public class TRuleReplaceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 规划疫苗剂次id
	private String planConsultId;
	// 疫苗id
	private String bioClassId;
	// 接种剂次
	private Integer agentTimes;
	// 替代类别：0，一对一替代，其他int类型为该值的疫苗同时替代
	private Integer replaceType;
	// 结束替代月龄
	private Integer endMonth;
	// 开始替代月龄
	private Integer startMonth;
	// 与上一几次间隔的天数
	private Integer intervalDay;

}
