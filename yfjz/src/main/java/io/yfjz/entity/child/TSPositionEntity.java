package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 省市县镇村数据
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-11-12 20:38:42
 */

@Getter
@Setter
@ToString
public class TSPositionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Long provinceId;
	//
	private String provinceName;
	//
	private Long cityId;
	//
	private String cityName;
	//
	private Long countyId;
	//
	private String countyName;
	//
	private Long townId;
	//
	private String townName;
	//
	private Long villageId;
	//
	private String villageName;

}
