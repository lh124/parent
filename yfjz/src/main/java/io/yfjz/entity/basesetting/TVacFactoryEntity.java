package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;
import lombok.*;



/**
 * 疫苗生产厂家
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 16:03:56
 */
@Setter
@Getter
public class TVacFactoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//厂家编码
	private String factoryCode;
	//厂家名称
	private String factoryName;
	//厂家简称
	private String factoryCnName;
	//状态:0：正常；-1：删除
	private Integer status;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//删除状态
	private Integer deleteStatus;
	//拼音首字母
	private String pinyinInitials;

}
