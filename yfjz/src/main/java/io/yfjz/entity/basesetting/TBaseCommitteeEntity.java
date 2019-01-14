package io.yfjz.entity.basesetting;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 居委会表(居委会/行政村)
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-28 10:45:07
 */
@Getter
@Setter
@ToString
public class TBaseCommitteeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//行政村编码
	private String code;
	//行政村名称
	private String name;
	//行政村名称拼音首字母
	private String pinyinInitials;
	//是否统计
	private Integer iscount;
	//机构编码
	private String orgId;
	//新增时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//删除状态：1：删除
	private Integer deleteStatus;

}

