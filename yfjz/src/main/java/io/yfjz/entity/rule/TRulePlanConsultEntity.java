package io.yfjz.entity.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 儿童应接种计划参照表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 17:48:42
 */

@Getter
@Setter
public class TRulePlanConsultEntity implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	/**未种*/
	public static final int STATE_NO =0;
	/**间短*/
	public static final int STATE_SHORT =1;
	/**及时*/
	public static final int STATE_TIMELY =2;
	/**合格*/
	public static final int STATE_QUALIFILED =3;
	/**超期*/
	public static final int STATE_OUT =4;
	/**提早*/
	public static final int STATE_ADVANCE =5;
	// 儿童规划程序表主键
	private String id;
	// 儿童编码/唯一标识
	private String childCode;
	// 疫苗类别标识，类别ID
	private String classId;
	//规划疫苗名称
	private String className;
	// 接种剂次
	private Integer injectionTimes;
	// 接种时间，为null时表示没有接种
	private Date inoculateTime;
	// 最早接种时间，儿童需达到这时间才可接种该疫苗
	private Date firstTime;
	// 及时时间，达到最早时间，小于等于该时间为及时
	private Date timelyTime;
	// 合格时间
	private Date qualifiledTime;
	//
	private Date lastTime;
	// 接种状态：0、未接种，1、间短，2、及时，3、合格，4、超期
	private Integer state;
	// 接种或替代针剂，接种信息ID
	private String inoculateId;
	// 最早月龄
	private Integer firstMonth;
	// 及时月龄
	private Integer timelyMonth;
	// 合格月龄
	private Integer qualifiledMonth;
	// 最晚接种月龄
	private Integer lastMonth;
	@Override
	public TRulePlanConsultEntity clone() {
		TRulePlanConsultEntity consult = null;
		try{
			consult = (TRulePlanConsultEntity)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return consult;
	}
}
