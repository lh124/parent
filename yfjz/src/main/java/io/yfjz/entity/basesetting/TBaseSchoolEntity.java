package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 入学入托机构表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 16:30:51
 */

@Getter
@Setter
public class TBaseSchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编码
	private String code;
	//名称
	private String name;
	//简称
	private String sortName;
	//组织
	private String orgId;
	//详细地址
	private String address;
	//联系电话
	private String phone;
	//联系人
	private String linkMan;
	//启用时间
	private Date createTime;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//更新时间
	private Date updateTime;

}
