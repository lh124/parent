package io.yfjz.entity.statistics;

import lombok.Getter;
import lombok.Setter;

/**
 * 建卡及时率VO
 * @author 邓召仕
 * @date 2018年4月1日
 */
@Getter
@Setter
public class CreateCardRate{

	private String committeeId;
	private String committeeName;
	private String childCount;
	private String createCardCount;
	private String createCardRate;
	private String timelyNumber;
	private String timelyRate;
	private String qualifiedNumber;
	private String qualifiedRate;

}
