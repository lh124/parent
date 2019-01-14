package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



/**
 * 免疫规划字典表
 * @作者：邓召仕
 * 下午5:54:57
 */

@Getter
@Setter
public class TRuleDicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键uuid
	private String id;
	//国家规划名称
	private String name;
	//规矩规划英文简称
	private String enName;
	//排序
	private Integer sortOrder;
	//疫苗对应的大类分类ID
	private String bioSortId;

}
