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
 * @tel 15685423726
 * @date 2018-07-23 15:50:15
 */
@Getter
@Setter
public class TMvUploadEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//uuid 主键
	private String id;
	//视频的真实名称
	private String realname;
	//视频的真实全路径
	private String url;
	//添加视频的时间
	private Date createTime;
	//文件大小
	private String filesize;

}
