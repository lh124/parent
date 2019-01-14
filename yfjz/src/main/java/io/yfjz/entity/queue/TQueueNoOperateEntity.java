package io.yfjz.entity.queue;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-25 02:25:55
 */

@Getter
@Setter
@ToString
public class TQueueNoOperateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//uuid
	private String id;
	//操作的queue_id
	private String fkQueueNoId;
	//操作的tower_id
	private String tower;
	//操作人
	private String operator;
	//操作类型
	private String operateType;
	//操作时间
	private Date operateTime;
	//备注
	private String remark;
	//状态
	private Integer status;

}
