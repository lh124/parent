package io.yfjz.entity.bus;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 用户评价表
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-09-05  17:10
 **/

@Getter
@Setter
@ToString
public class TBusEvaluateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String fkDoctorId;
	//评价标准，1差评，2一般，3满意，4非常满意
	private Integer evaluateType;
	//状态 0:正常 -1 ：删除
	private Integer status;
	//创建时间
	private Date createTime;
	//组织机构
	private String orgid;
	//儿童编码
	private String fkChildCode;
	//接种台
	private String towerId;

}
