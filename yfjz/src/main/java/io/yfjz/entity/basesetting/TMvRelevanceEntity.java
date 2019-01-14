package io.yfjz.entity.basesetting;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685·423726
 * @date 2018-07-23 17:24:51
 */
@Getter
@Setter
public class TMvRelevanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//uuid 主键
	private String id;
	//视频的id
	private String mvId;
	//播放台id；1：大厅视频；2：留观视频
	private String playerareaId;
	//视频的真实全路径
	private String fileurl;
	//添加视频的时间
	private Date createTime;

}
