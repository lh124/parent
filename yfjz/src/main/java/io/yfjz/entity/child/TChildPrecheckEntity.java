package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 儿童预检信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-23 10:05:06
 */

@Getter
@Setter
@ToString
public class TChildPrecheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	//体温检测
	private String temp;
	//体检结果 0合格，1不合格
	private String result;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	//状态,0正常,1停用
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//机构编码
	private String orgId;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//是否来源平台 0:本地 ，1：平台
	private Integer type;
	//儿童编号
	private String chilCode;

}
