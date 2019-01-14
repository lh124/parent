package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



/**
 * 母亲感染HIV所生儿童接种规则表
 * @作者：邓召仕
 * 下午5:56:06
 */
@Getter
@Setter
public class TRuleHivEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键uuid
	private String id;
	//hiv感染状态：1、感染；2、感染不详；3、未感染
	private Integer hivType;
	//是否有症状或免疫抑制：1、有症状；2、无症状
	private Integer symptomType;
	//不可或暂缓接种的规划疫苗类别ID，多个疫苗类别用逗号隔开
	private String cannotPlanClass;

}
