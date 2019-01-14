package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 疫苗入库记录汇总表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-30 09:40:37
 */

@Getter
@Setter
public class TMgrStockInTotalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//编号
	private String id;
	//入库类型：1疫苗入库、2归还疫苗
	private Integer inType;
	//入库人员
	private String fkInStockUser;
	//入库时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date storageTime;
	//备注
	private String remark;
	//状态 0：正常  -1：删除
	private Integer status;
	//创建人
	private String fkCreateUserId;
	//创建时间
	private Date createTime;
	//组织
	private String orgId;
	//入库单号
	private String stockInCode;
	//仓库ID
	private TMgrStoreEntity store;

}
