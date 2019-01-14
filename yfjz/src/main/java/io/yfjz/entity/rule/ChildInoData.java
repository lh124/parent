package io.yfjz.entity.rule;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 儿童接种数据，保存规则用到的儿童接种信息
 * @作者：邓召仕
 * 2018年7月27日上午11:27:19
 */
@Getter
@Setter
public class ChildInoData implements Serializable {

	/**
	 * @作者：邓召仕
	 * 2018年7月27日上午11:27:15
	 */
	private static final long serialVersionUID = 1L;
	//
	private String id;
	//儿童编码
	private String chilCode;
	//疫苗编码
	private String inocBactCode;
	//剂次
	private Integer inocTime;
	//接种日期
	private Date inocDate;
	//评价(及时,合格,超期,首针提前,间短)
	private String inocOpinion;
	//疫苗监管码
	private String inocRegulatoryCode;
}
