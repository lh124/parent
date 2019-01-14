package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 机构接种日设置表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-08 10:28:25
 */

@Getter
@Setter
public class TBaseDaySettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键 guid
	private String id;
	//接种日设置类型 1. 接种点-周设置，2.接种点-居委会/行政村-周设置，3.接种点-疫苗-周设置，4.接种点-月设置，5.接种点-居委会/行政村-月设置，6.接种点-疫苗-月设置
	private String settingType;
	//接种点编码 类似5201110301
	private String orgId;
	//疫苗编码 4位
	private String vaccCode;
	//行政村/居委会编码
	private String committeeId;
	//接种点设置的接种日，如果接种日设置类型为1, 该字段保存1到7之间的集合（json对象的字符），如果接种日设置类型为4，该字段保存1到31之间的集合，如果接种日设置类型为其他值，该值不设置
	private String days;
	//状态：0：启用；-1：禁用
	private Integer status;
	//创建时间
	private Date createTime;

}
