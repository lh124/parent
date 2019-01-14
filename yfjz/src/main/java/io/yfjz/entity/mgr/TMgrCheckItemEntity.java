package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 盘点明细表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-07 15:56:59
 */

@Getter
@Setter
public class TMgrCheckItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//盘点状态
	/**
	 *正常
	 */
	public static final int STATUS_NORMAL=0;
	/**
	 * 盘亏
	 */
	public static final int STATUS_DOWN=-1;
	/**
	 * 盘盈
	 */
	public static final int STATUS_UP=1;

	//id
	private String id;
	//库存信息ID
	private String fkStockInfoId;
	//系统数量
	private Long sysNumber;
	//实际数量
	private Long realNumber;
	//差异数量
	private Long diffNumber;
	//差异原因
	private String cause;
	//处理方法
	private String handle;
	//盘点状态
	private Integer checkStatus;
	//盘点汇总ID
	private String fkCheckTotalId;
	//盘点汇总ID
	private Date createTime;

}
