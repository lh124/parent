package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/** 
* @Description: 疫苗报损表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/4 11:00
* @Tel  17328595627
*/ 
@Getter
@Setter
public class TMgrWastageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//库存疫苗信息ID
	private String stockInfoId;
	//耗损 类型
	private Integer type;
	//疫苗数量
	private Long amount;
	//疫苗人份数量
	private Long personAmount;
	//机构ID
	private String orgId;
	//报损原因
	private String remark;
	//填报人
	private String applyUser;
	//状态
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
