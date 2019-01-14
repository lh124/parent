package io.yfjz.entity.print;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 儿童打印基本信息打印坐标
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 11:27:06
 */

@Getter
@Setter
@ToString
public class TChildInfoPrintPointEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//儿童属性
	private String childProperties;
	//属性X坐标
	private String propertiesX;
	//属性Y坐标
	private String propertiesY;
	//1为接种证打印,2为接种卡打印
	private String printtype;
	//接种状态：0、未接种，1、间短，2、及时，3、合格，4、超期
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
