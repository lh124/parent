package io.yfjz.entity.child;

import io.yfjz.entity.PersistentEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



/**
 * 儿童基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */

@Getter
@Setter
public class TChildInfoEntity implements Serializable, PersistentEntity {
	private static final long serialVersionUID = 1L;
	
	//儿童编码
	private String chilCode;
	//条形码
	private String chilBarcode;
	//姓名
	private String chilName;
	//性别
	private String chilSex;
	//出生日期
	private Date chilBirthday;
	//出生体重
	private Double chilBirthweight;
	//民族
	private String chilNatiId;
	//免疫卡号
	private String chilCardno;
	//身份证号
	private String chilNo;
	//出生证号
	private String chilBirthno;
	//母亲姓名
	private String chilMother;
	//父亲姓名
	private String chilFather;
	//行政村/居委会
	private String chilCommittee;
	//学校
	private String chilSchool;
	//户籍地址
	private String chilHabiaddress;
	//家庭住址
	private String chilAddress;
	//家庭电话
	private String chilTel;
	//手机
	private String chilMobile;
	//发卡日期
	private Date chilCarddate;
	//健康档案标识
	private String chilHealthno;
	//平台字段,未知属性
	private String chilResidenceclient;
	//居住属性
	private String chilResidence;
	//户籍属性
	private String chilAccount;
	//户籍县国标
	private String chilHabiId;
	//平台字段,未知属性
	private String chilInoctype;
	//过敏史
	private String chilSensitivity;
	//母亲HB
	private String chilMotherhb;
	//母亲HIV
	private String chilMotherhiv;
	//个案状态
	private String chilHere;
	//在册变化日期
	private Date chilLeavedate;
	//迁出分类
	private String chilLeave;
	//迁出其他备注
	private String chilLeaveremark;
	//现管理单位编码
	private String chilCurdepartment;
	//前管理单位编码
	private String chilPredepartment;
	//建档县国标
	private String chilCreatecounty;
	//建档单位编码
	private String chilCreatesite;
	//建档日期
	private Date chilCreatedate;
	//建卡人
	private String chilCreateman;
	//受种者单位
	private String chilUnitimmu;
	//出生医院编码
	private String chilBirthhospital;
	//出生医院名称
	private String chilBirthhospitalname;
	//是否有卡介苗疤痕
	private String chilBcgScar;
	//母亲身份证号
	private String chilMotherno;
	//父亲身份证号
	private String chilFatherno;
	//出省状态
	private String chilProvince;
	//更新时间
	private Date chilEditDate;
	//建卡时间
	private Date createCardTime;
	//type,0本地,1平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//状态,0正常,-1删除
	private Integer status;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//备注
	private String remark;

	private String chilHabiaddressTownId;

	private String chilAddressTownId;

	//接收参数用
	private String flag;
	private Date updateTime;

}
