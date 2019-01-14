package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 儿童迁移记录表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 15:56:11
 */

@Getter
@Setter
@ToString
public class TChildMoveEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键编号
	private String id;
	//儿童id,平台数据有,不是儿童编码
	private String chheChilId;
	//儿童编码
	private String chilCode;
	//现在册状态
	private String chheHere;
	//原在册状态
	private String chhePrehere;
	//出省状态
	private String chheChilProvince;
	//变更原因
	private String chheChilProvinceremark;
	//变更时间
	private Date chheChangedate;
	//变更单位编码
	private String chheDepaId;
	//是否来源平台 0:本地 ，1：平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//状态：0：正常；-1：删除
	private Integer status;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//机构编码
	private String orgId;


}
