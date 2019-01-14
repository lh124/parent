package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 疫苗入库记录明细表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-30 09:40:37
 */

@Getter
@Setter
public class TMgrStockInItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//编号
	private String id;
	//库存疫苗信息表id
	private String fkStockInfoId;
	//入库数量
	private Long amount;
	//入库设备ID
	private String fkEquipmentId;
	//状态 0：正常  -1：删除
	private Integer status;
	//供货负责人
	private String provider;
	//人份数量
	private Long personAmount;
	//供货单位
	private String providerFactory;
	//汇总记录ID
	private String totalId;
	//创建时间
	private Date createTime;
	//报损人份
	private Long destoryAmount;
	//使用人份
	private Long useAmount;
	//领取数量
	private Long receiveAmount;
	//归还的工作台ID
	private String towerId;


}