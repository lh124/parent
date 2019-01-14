package io.yfjz.entity.queue;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




/**
 * 
 *
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-24 14:05:41
 */

@Getter
@Setter
@ToString
public class TQueueNoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//uuid
	private String id;
	//号码
	private Integer no;
	//
	private String noText;
	//类型：0本地，1网络
	private Integer type;
	//儿童编码
	private String childCode;
	//儿童姓名
	private String childName;
	//取号时间
	private Date createTime;
	//当前步骤
	private String step;
	//当前位置
	private String position;
	//状态 -2退回 -1取消，0正常
	private Integer status;
	//取消备注
	private String remark;


	//非持久化字段
	//动作
	private QueueAction action;
	//操作人
	private String operator;
	//目标(退回消息需要退回给上一步操作台)
	private String dest;
	//
	private String src;
	//登记疫苗
	private String vaccine;


}
