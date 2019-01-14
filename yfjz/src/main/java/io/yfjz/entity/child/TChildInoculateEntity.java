package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 儿童接种记录表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 14:02:29
 */

@Getter
@Setter
public class TChildInoculateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//儿童编码
	private String chilCode;
	//疫苗编码
	private String inocBactCode;
	//接种属性
	private String inocProperty;
	//剂次
	private Integer inocTime;
	//接种日期
	private Date inocDate;
	//接种单位机构编码
	private String inocDepaCode;
	//强化属性
	private String inocReinforce;
	//批号
	private String inocBatchno;
	//剂量
	private String inocDose;
	//评价(及时,合格,超期,首针提前,间短)
	private String inocOpinion;
	//联合疫苗编码
	private String inocUnionCode;
	//疫苗失效期
	private Date inocValiddate;
	//生产企业(编码)
	private String inocCorpCode;
	//疫苗监管码
	private String inocRegulatoryCode;
	//是否补助疫苗1是,0不是
	private String inocPay;
	//是否免费疫苗1免费,0收费
	private String inocFree;
	//部门id(平台数据)
	private String inocDepaId;
	//接种县国标(机构编码前6位)
	private String inocCounty;
	//接种单位编码(机构编码后四位)
	private String inocNationcode;
	//接种部位(存码表value值)
	private String inocInplId;
	//接种途径
	private String channel;
	//接种护士
	private String inocNurse;
	//未知,保留字段
	private String inocVcnKind;
	//未知,保留字段(平台产生)
	private String zxhs;
	//未知,保留字段
	private String inocUseticket;
	//接种记录修改单位机构编码(存下载时的值)
	private String inocModifyCode;
	//修改记录时间
	private Date inocEditdate;
	//离开时间
	private Date leaveTime;
	//0:本地；1：平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//状态：0：正常；-1：删除
	private Integer status;
	//创建人编码
	private String createUserId;
	//添加记录时间
	private Date createTime;
	//备注
	private String remark;

	private String otherDepa;//外省接种单位



}
