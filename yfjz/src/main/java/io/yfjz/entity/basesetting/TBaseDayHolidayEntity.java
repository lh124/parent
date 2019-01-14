package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 机构节假日设置表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-08-30 10:43:53
 */

@Getter
@Setter
@ToString
public class TBaseDayHolidayEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键 guid
	private String id;
	//节假日说明，如：国庆节
	private String holidayName;
	//月份
	private String month;
	//日期：节假日具体日期，一年设一次、例如：[1,4,8,12]
	private String day;
	//接种点编码 类似5201110301
	private String orgId;

}
