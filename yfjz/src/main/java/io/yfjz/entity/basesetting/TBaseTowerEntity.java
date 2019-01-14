package io.yfjz.entity.basesetting;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;



/**
 * 工作台基本信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-24 11:01:00
 */
@Getter
@Setter
public class TBaseTowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String orgId;
	private String towerName;
	/**
	 * 工作台属性：
	 * 1为登记台
	 * 2为接种台
	 * 3儿保
	 * 4为预检
	 */
	private Integer towerNatureId;
	private Integer status;
	private Date createTime;
	private String createUserId;
	/**
	 * 删除状态：0：正常；-1：删除
	 */
	private Integer deleteStatus;
	/**
	 * 更新时间
	 */
	private Date updateTime;


	//    ************************不参与数据库持久化*******************************
	@Transient
	private String orgName;

}
