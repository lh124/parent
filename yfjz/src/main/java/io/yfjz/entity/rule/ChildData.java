package io.yfjz.entity.rule;

import java.io.Serializable;
import java.util.Date;

import io.yfjz.entity.PersistentEntity;
import io.yfjz.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 儿童数据，保存规则用到的儿童信息
 * 
 * @作者：邓召仕 2018年7月27日上午11:27:19
 */
@Getter
@Setter
public class ChildData implements Serializable,Cloneable , PersistentEntity {

	/**
	 * @作者：邓召仕 2018年7月27日上午11:27:15
	 */
	private static final long serialVersionUID = 1L;
	// 儿童编码
	private String chilCode;
	// 姓名
	private String chilName;
	// 性别
	private String chilSex;
	// 出生日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date chilBirthday;

	//应种通知字段
	private String committee;//行政村
	private String fatherName;//父亲姓名
	private String matherName;//母亲姓名
	private String chilTel;//家庭电话
	private String chilMobile;//手机
	private String address;//联系地址
	private String planId;//规划疫苗ID
	private String planName;//规划疫苗名称
	private String times;//剂次
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inoDate;//计划日期
	private String chilHere;//个案状态
	private String here;//个案状态
	private String orgId;//组织机构编码
	/**
	 * 以下字段批量补录使用
	 * @author 田金海
	 * @date 2018/09/04
	 */
	private String id;//插入数据库的id
	private String selectCondition;//查询条件
	private String selectTimes;//查询编号
	private String remark;//备注
	/**
	 * 获取：儿童当前月龄(自然月)
	 * 
	 * @作者：邓召仕 2018年7月27日
	 * @return
	 */
	public int getMoonAge() {
		if (chilBirthday == null) {
			return 0;
		} else {
			return DateUtils.calDiffMonth(chilBirthday, new Date());
		}
	}

	/**
	 * 获取：儿童指定日期的月龄（自然月）
	 * 
	 * @作者：邓召仕 2018年7月27日
	 * @return
	 */
	public int getMoonAge(Date date) {
		return DateUtils.calDiffMonth(chilBirthday, date);
	}

	@Override
	public ChildData clone(){
		ChildData childData = null;
		try {
			childData = (ChildData) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return childData;
	}
}
