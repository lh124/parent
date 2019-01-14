package io.yfjz.utils.page;

import java.io.Serializable;

/**
 * @author :zhangdong
 * @Description : 分页插件
 * @date 10:13 2018/4/18
 */
public class PageParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6297178964005032338L;

	// bootstrap table查询用户参数
	private Integer offset;
	private String search;

	// 兼容其他系统移植过来的参数
	private Integer pageNo; // 页号
	private Integer pageSize; // 每页大小


	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
