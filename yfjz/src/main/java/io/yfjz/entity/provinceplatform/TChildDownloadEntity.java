package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 省平台下载新生儿表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 14:33:28
 */

@Getter
@Setter
@XStreamAlias("newborn")
public class TChildDownloadEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@GeneratedValue(generator = "system-uuid")
	private String id;
	//乙肝未种原因
	@XStreamAlias("nebi_hepb_reason")
	private String nebiHepbReason;
	//新增时间
	@XStreamAlias("nebo_adddate")
	private Date neboAdddate;
	//地址
	@XStreamAlias("nebo_address")
	private String neboAddress;
	//现住址居委会
	@XStreamAlias("nebo_address_community")
	private String neboAddressCommunity;
	//现住址乡镇名称
	@XStreamAlias("nebo_address_depa_id_name")
	private String neboAddressDepaIdName;
	//现住址乡镇
	@XStreamAlias("nebo_address_habi_id")
	private String neboAddressHabiId;
	//新增用户
	@XStreamAlias("nebo_adduser")
	private String neboAdduser;
	//新生儿关联
	@XStreamAlias("nebo_binding")
	private String neboBinding;
	//出生时间
	@XStreamAlias("nebo_birthtime")
	private Date neboBirthtime;
	//介质卡号
	@XStreamAlias("nebo_cardno")
	private String neboCardno;
	//儿童编码
	@XStreamAlias("nebo_code")
	private String neboCode;
	//部门下的新生儿数
	@XStreamAlias("nebo_count")
	private String neboCount;
	//下载mac
	@XStreamAlias("nebo_downmac")
	private String neboDownmac;
	//下载类型
	@XStreamAlias("nebo_downtype")
	private String neboDowntype;
	//父亲姓名
	@XStreamAlias("nebo_father")
	private String neboFather;
	//父亲电话
	@XStreamAlias("nebo_father_tel")
	private String neboFatherTel;
	//父亲身份证
	@XStreamAlias("nebo_fatherno")
	private String neboFatherno;
	//父亲HBsag
	@XStreamAlias("nebo_fhbsag")
	private String neboFhbsag;
	//户籍地乡镇
	@XStreamAlias("nebo_habi_depa_id")
	private String neboHabiDepaId;
	//户籍地乡镇名称
	@XStreamAlias("nebo_habi_depa_id_name")
	private String neboHabiDepaIdName;
	//户籍县国标
	@XStreamAlias("nebo_habi_id")
	private String neboHabiId;
	//户籍地居委会
	@XStreamAlias("nebo_habiaddress_community")
	private String neboHabiaddressCommunity;
	//出生小时
	@XStreamAlias("nebo_hour")
	private String neboHour;
	//新生儿id
	@XStreamAlias("nebo_id")
	private String neboId;
	//母亲HBsag
	@XStreamAlias("nebo_mhbsag")
	private String neboMhbsag;
	//手机
	@XStreamAlias("nebo_mobile")
	private String neboMobile;
	//母亲姓名
	@XStreamAlias("nebo_mother")
	private String neboMother;
	//母亲电话
	@XStreamAlias("nebo_motherTel")
	private String neboMotherTel;
	//母亲身份证
	@XStreamAlias("nebo_motherno")
	private String neboMotherno;
	//姓名
	@XStreamAlias("nebo_name")
	private String neboName;
	//民族
	@XStreamAlias("nebo_natiId")
	private String neboNatiId;
	//下载个数
	@XStreamAlias("nebo_packcount")
	private String neboPackcount;
	//居住属性
	@XStreamAlias("nebo_residence")
	private String neboResidence;
	//性别
	@XStreamAlias("nebo_sex")
	private String neboSex;
	//联系电话
	@XStreamAlias("nebo_tel")
	private String neboTel;
	//出生体重
	@XStreamAlias("nebo_weight")
	private String neboWeight;
	//添加记录时间
	private Date createtime;
	@Transient
	@XStreamAlias("newbornInoculates")
	private List<NewbornInoculate> inoculationList = new ArrayList<>();



}
