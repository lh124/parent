package io.yfjz.entity.child;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 儿童异常反应表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */

@Getter
@Setter
public class TChildAbnormalEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//儿童编码
	private String chilCode;
	//疫苗编码
	private String aefiBactCode;
	//反应症状编码
	private String aefiCode;
	//反应日期
/*	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")*/
	private Date aefiDate;
	//0:本地；：平台
	private Integer type;
	//同步状态,0未同步;1同步
	private Integer syncstatus;
	//0:正常；-1删除
	private Integer deleteStatus;
	//创建人id
	private String createUserId;
	//创建人名字
	private String createUserName;
	//创建时间
	private Date createTime;
	//机构编码
	private String orgId;

}
