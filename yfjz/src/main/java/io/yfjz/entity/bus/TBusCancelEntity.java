package io.yfjz.entity.bus;

import java.io.Serializable;
import java.util.Date;
import lombok.*;



/**
 * 取消原因表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-26 09:54:38
 */
@Setter
@Getter
public class TBusCancelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//服务编码  取消的业务流程流水号
	private String fkServiceCode;
	//工作台
	private String posId;
	//操作用户id
	private String fkOperateUserId;
	//操作时间
	private Date createTime;
	//取消原因
	private String content;
	//状态：0：正常；-1：删除
	private Integer status;
	//儿童编码
	private String chilCode;

	private String  bioCode;

	private String createName;

	private String chilName;
private String bioName;

}
