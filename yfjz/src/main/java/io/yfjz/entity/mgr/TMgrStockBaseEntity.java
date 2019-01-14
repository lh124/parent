package io.yfjz.entity.mgr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 库存产品基础信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 11:13:14
 */

@Getter
@Setter
public class TMgrStockBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//疫苗id
	private String fkVaccineCode;
	//产品名称
	private String productName;
	//批号
	private String batchnum;
	//到期时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;
	//状态 0:正常 -1：删除
	private Integer status;
	//创建人
	private String fkCreateUserId;
	//创建时间
	private Date createTime;
	//组织
	private String orgId;
	//接种部位
	private String inoculationSite;
	/**
	 * 接种途径
	 */
	private String channel;
	//生产厂家名称
	private String factoryName;
	//生产厂家
	private String factoryId;
	//规格
	private String dosenorm;
	//剂型
	private String drug;
	//计量单位
	private String doseminUnitCode;
	//备注
	private String remark;
	//人份转换：人份=库存×人份转换
	private Float conversion;
	//疫苗单价
	private BigDecimal price;
	//批签发
	private String lotRelease;
	//批准文号
	private String licenseNumber;
	//类型 0；疫苗，1：其他耗材
	private Integer type;


}
