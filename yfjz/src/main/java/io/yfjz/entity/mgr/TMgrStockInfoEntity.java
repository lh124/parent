package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 疫苗库存信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-30 09:53:37
 */


@Getter
@Setter
public class TMgrStockInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//主键
	private String id;
	//疫苗基础信息表id
	private String fkBaseInfo;
	//库存量
	private Long amount;
	//状态 0:正常 -1：删除
	private Integer status;
	//创建人
	private String fkCreateUserId;
	//创建时间
	private Date createTime;
	//人份总数
	private Long personAmount;
	//使用人份数量
	private Long useAmount;
	//剩余人份数量
	private Long remainAmount;
	//领取人份数量
	private Long receiveAmount;
	//仓库ID
	private String fkStoreId;
	//设备ID
	private String fkEquipmentId;
	//使用人份

	//***********************人份转化***************************//
	private Integer conversion;

}
