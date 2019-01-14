package io.yfjz.entity.child;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 省市县镇村数据
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 13:41:54
 */
@Setter
@Getter
public class TBasePositionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String provinceId;
	//
	private String provinceName;
	//
	private String cityId;
	//
	private String cityName;
	//
	private String countyId;
	//
	private String countyName;
	//
	private String hosptialId;
	//
	private String hosptialName;
	//
	private String townId;
	//
	private String townName;

}
