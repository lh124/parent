package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 儿童禁忌表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:47:59
 */

@Getter
@Setter
public class TChildTabooEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键编号
	private String id;
	//儿童编码
	private String chilCode;
	//接种禁忌代码
	private String istaCode;
	//疫苗代码
	private String istaBioCode;
	//检测日期(保留字段)
	private Date istaCheckDate;
	//type,0本地,1平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//状态,0正常,-1删除
	private Integer status;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//备注
	private String remark;

	private Integer deleteStatus;


}
