package io.yfjz.entity.basesetting;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;




/**
 * 儿童出生医院
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 17:05:08
 */

@Getter
@Setter
public class TBaseHospitalEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//医院编号
	private String hospitalCode;
	//医院名称
	private String hospitalName;
	//医院名称拼音首字母
	private String pinyinInitials;
	//医院所在区域编码
	private String orgAreaCode;
	//状态: 0：正常；-1：删除
	private Integer status;
	//启用时间
	private Date createTime;
	//删除状态：0：正常；1：删除
	private Integer deleteStatus;
	//更新时间
	private Date updateTime;

}
