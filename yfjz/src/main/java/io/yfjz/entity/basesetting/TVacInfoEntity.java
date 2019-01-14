package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 疫苗信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-08 14:54:16
 */

@Getter
@Setter
@ToString
public class TVacInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//疫苗编码
	private String bioCode;
	//疫苗名称
	private String bioName;
	//疫苗分类
	private String bioClassId;
	//疫苗活性  减毒；灭活
	private String bioType;
	//接种途径
	private Integer bioSpecId;
	//疫苗中文简称
	private String bioCnSortname;
	//疫苗英文简称
	private String bioEnSortname;
	//拼音首字母
	private String pinyinInitials;
	//状态
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
