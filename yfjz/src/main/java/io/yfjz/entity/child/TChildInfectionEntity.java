package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 儿童传染病表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:37:19
 */

@Getter
@Setter
@ToString
public class TChildInfectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//儿童编码
	private String chilCode;
	//传染病编码
	private String infeCode;
	//发病日期
	private Date infeDate;
	//0:本地；：平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//0:正常；-1删除
	private Integer deleteStatus;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//机构编码
	private String orgId;

}
