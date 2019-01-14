package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 角色与菜单对应关系
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-17 13:57:20
 */

@Getter
@Setter
public class SysDepartUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//机构编码ID
	private String orgId;
	//用户编码ID
	private String userId;

}
