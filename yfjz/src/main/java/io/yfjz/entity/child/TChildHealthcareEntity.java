package io.yfjz.entity.child;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



/**
 * 儿童体检记录表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-24 15:22:38
 */
@Setter
@Getter
public class TChildHealthcareEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	//儿童编码code，t_sys_child
	private String chilCode;
	//体检内容
	private String content;
	//登记日期
	private Date registerDate;
	//登记医生id，t_s_user
	private String registerUserId;
	//1：合格；2：需复检；3：不合格
	private Integer result;
	//身高
	private Float height;
	//体重
	private Float weight;
	//头围
	private Float headCirc;
	//囟门
	private Float fontanelA;
	//囟门
	private Float fontanelB;
	//出牙数
	private Integer toothNum;
	//血红蛋白浓度
	private Integer hemoglobinVal;
	//是否服用维生素d
	private String takedVitd;
	//备注
	private String remark;
	//状态:0：正常；-1删除
	private Integer deleteStatus;
	//创建人
	private String createUserId;
	//创建时间
	private Date createTime;
	//组织
	private String orgId;




}
