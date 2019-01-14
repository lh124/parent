package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 儿童过敏表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */

@Getter
@Setter
@ToString
public class TChildAllergyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键编号
	private String id;
	//儿童表ID
	private String chilCode;
	//过敏信息
	private String describes;
	//登记人
	private String registrant;
	//检测日期
	private Date checkDate;
	//type,0本地,1平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//状态,0正常,-1删除
	private Integer deleteStatus;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//备注
	private String remark;
	//过敏疫苗
	private String bioCode;


}
