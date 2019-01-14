package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-05 10:29:20
 */
@Getter
@Setter
public class SysHash256Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//hashcode，文件生成的hash值
	private String hashcode;
	//文件的真实名称
	private String filename;
	//文件上传的时间
	private Date uploadtime;

}
