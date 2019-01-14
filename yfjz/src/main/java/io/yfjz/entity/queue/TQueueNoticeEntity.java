package io.yfjz.entity.queue;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TQueueNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//显示屏类别（大厅、留观）
	private String type;
	//是否显示通知
	private Integer show;
	//通知内容
	private String content;

}
