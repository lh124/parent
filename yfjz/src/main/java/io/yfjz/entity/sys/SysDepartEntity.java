package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;



/**
 * 机构、部门信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-16 23:33:07
 */

@Getter
@Setter
@ToString
public class SysDepartEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private String id;
	//部门名称
	private String name;
	//部门描述
	private String description;
	//上级部门
	private String pid;
	//状态：0:启用：1：禁用
	private Integer status;
	//创建时间
	private Date createTime;

	private Integer deleteStatus;
}
