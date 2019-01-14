package io.yfjz.entity.mgr;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;



/** 
* @Description: 设备管理 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 15:31
* @Tel  17328595627
*/

@Getter
@Setter
public class TMgrEquipmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//关联的仓库
	private TMgrStoreEntity store ;
	//设备名称 冰箱1，冰箱2，冰箱3
	private String name;
	//设备编码
	private String code;
	//状态,0正常,-1删除
	private Integer status=0;
	//创建人id
	private String createUserId;
	//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	//备注
	private String remark;
	//组织机构Id
	private String orgId;
}
