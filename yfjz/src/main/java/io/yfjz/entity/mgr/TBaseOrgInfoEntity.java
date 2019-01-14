package io.yfjz.entity.mgr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 卫生院基本信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-04 17:03:45
 */

@Getter
@Setter
@ToString
public class TBaseOrgInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//机构编码
	private String orgId;
	//机构名称
	private String orgName;
	//机构地址
	private String address;
	//负责人邮箱
	private String email;
	//负责人
	private String manager;
	//负责人电话
	private String phone;
	//0:正常；-1删除
	private Integer status;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//备注说明
	private String remark;

}
