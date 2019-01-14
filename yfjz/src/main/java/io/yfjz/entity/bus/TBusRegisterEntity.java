package io.yfjz.entity.bus;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 儿童接种登记表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 14:25:03
 */

@Getter
@Setter
@ToString
public class TBusRegisterEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//儿童编号
	private String fkChildCode;
	//接种单位编码
	private String fkOrgId;
	//疫苗代码
	private String fkVaccineCode;
	//针次
	private Integer doseNo;
	// 疫苗批号
	private String batchnum;
	//过期日期
	private Date expiryDate;
	//生产企业代码
	private String factoryCode;
	//登记医生
	private String createDoctorName;
	private String createDoctorId;
	//接种部位
	private String inoculateSite;
	//接种部位编码，字典表的value值
	private Integer inoculateSiteCode;
	//备注
	private String remark;
	//排列编号
	private Integer order;
	//状态 1:删除
	private Integer deleteStatus;
	//创建时间
	private Date createTime;
	//登记台ID
	private String fkPosId;
	//疫苗是否收费，0为收费，1为免费
	private Integer ismf;
	//是否已经完成接种 默认登记状态，未完成注射 ：默认：0未完成；1已完成
	private Integer status;

}
