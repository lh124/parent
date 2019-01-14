package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 疫苗出库记录明细表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */

@Getter
@Setter
@ToString
public class TMgrStockOutItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private String id;
	//库存信息id
	private String stockInfoId;
	//出库数量
	private Long amount;
	//人份数量
	private Long personAmount;
	//创建人
	private String createUserId;
	//汇总表ID
	private String totalId;
	//状态
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//设备ID
	private String fkEquipmentId;
}
