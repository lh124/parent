package io.yfjz.entity.print;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 儿童打印接种记录模板坐标
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 11:27:06
 */

@Getter
@Setter
@ToString
public class TChildPrintModelPointEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//接种疫苗
	private String bioName;
	//疫苗唯一标识名
	private String ename;
	//接种日期X
	private Integer inoculateDatex;
	//接种日期Y
	private Integer inoculateDatey;
	//接种部位X
	private Integer textx;
	//接种部位Y
	private Integer texty;
	//疫苗批号X
	private Integer batchnumx;
	//疫苗批号Y
	private Integer batchnumy;
	//生产企业X坐标
	private Integer factoryNamex;
	//生产企业X坐标
	private Integer factoryNamey;
	//接种单位X坐标
	private Integer departnamex;
	//接种单位Y坐标
	private Integer departnamey;
	//接种医生X
	private Integer doctorx;
	//接种医生Y坐标
	private Integer doctory;
	//失效期坐标X
	private Integer expirationDatex;
	//失效期坐标Y
	private Integer expirationDatey;
	//打印选择类型
	private String printtype;
	//状态,0正常,1停用
	private Integer status;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

}
