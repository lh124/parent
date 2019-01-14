package io.yfjz.entity.provinceplatform;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 异地儿童接种信息
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-12 11:22:13
 */

@Getter
@Setter
@ToString
public class TChildElsewhereEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//儿童编码
	private String migrChildcode;
	//异动日期
	private Date migrDate;
	//异地接种单位编码
	private String migrDepaCode;
	//本地服务器时间
	private Date migrServerdate;
	//1异地迁入  2临时接种
	private String migrVaccState;

}
