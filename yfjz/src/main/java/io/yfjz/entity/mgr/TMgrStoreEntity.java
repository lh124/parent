package io.yfjz.entity.mgr;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


/**
* @Description: 仓库实体 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 13:41
* @Tel  17328595627
*/


@Getter
@Setter
public class TMgrStoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int TYPE_MAIN=0;//主仓库
	public static final int TYPE_TOWER=1;//接种台仓库


	//
	private String id;
	//仓库名称
	private String name;
	//仓库地址
	private String address;
	//管理员
	private String mgrUserId;
	//管理员电话
	private String mgrPhone;
	//管理员邮箱
	private String mgrEmail;
	//备注
	private String remark;
	//仓库类型 0：主仓库，1：接种台仓库
	private Integer ttype;
	//是否启用 0：启用 1 暂停
	private Integer status=0;
	//仓库关联的接种台ID
	private String posId;
	//组织机构编码
	private String orgId;
	//添加人Id
	private String createUserId;
	//添加时间
	private Date createTime;

	//仓库名称
	private String storeName;
}
