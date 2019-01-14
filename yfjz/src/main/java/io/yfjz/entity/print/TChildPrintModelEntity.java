package io.yfjz.entity.print;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 儿童打印模板
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 11:27:05
 */

@Getter
@Setter
@ToString
public class TChildPrintModelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//机构编号
	private String orgid;
	//模板名称
	private String modelName;
	//xml文件名称
	private String xmlName;
	//用户名
	private String username;
	//接种证模板值为1,接种卡模板值为2
	private String remark;
	//状态,0正常,1停用
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
