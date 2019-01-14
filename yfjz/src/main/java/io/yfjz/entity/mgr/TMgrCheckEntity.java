package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;




/**
 * 盘点表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-08 13:39:23
 */

@Getter
@Setter
public class TMgrCheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//盘点单号
	private String checkCode;
	//盘点仓库ID
	private String fkStoreId;
	//盘点时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date checkTime;
	//盘点人
	private String fkCheckUserId;
	//创建人
	private String createUserId;
	//状态,0正常,1停用
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	//仓库名称
	private String storeName;
}
