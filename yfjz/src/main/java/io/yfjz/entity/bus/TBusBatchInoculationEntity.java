package io.yfjz.entity.bus;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 批量补录表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-03 17:57:02
 */

@Getter
@Setter
@ToString
public class TBusBatchInoculationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//儿童ID
	private String childcode;
	//居委会/行政村
	private String committee;
	//儿童姓名
	private String cname;
	//性别,1男2女
	private String sex;
	//出生日期
	private String birthtime;
	//母亲姓名
	private String mothername;
	//父亲姓名
	private String fathername;
	//个案状态
	private String childInfoStatus;
	//电话
	private String telepone;
	//手机
	private String mobile;
	//通讯地址
	private String contactaddress;
	//疫苗
	private String bioName;
	//剂次
	private String agenttimes;
	//接种日期
	private String inoculateDate;
	//接种点ID
	private String orgid;

}
