package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 疫苗出库记录表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */

@Getter
@Setter
@ToString
public class TMgrStockOutTotalEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private String id;
	//出库人员
	private String outStockUserId;
	//出库类型:1领疫苗，2回退，3退货,
	private Integer outType;
	//出库仓库
	private String storeId;
	//入库仓库
	private String inStoreId;
	//备注
	private String remark;
	//创建人
	private String createUserId;
	//组织
	private String orgId;
	//出库单号
	private String stockOutCode;
	//状态
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
