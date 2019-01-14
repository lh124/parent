package io.yfjz.utils.page;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.ListUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author :zhangdong
 * @Description : 分页插件
 * @date 10:13 2018/4/18
 */
@Getter
@Setter
@ToString
public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 380769535919462244L;
	private int currentPageNo;
	private int pageSize;
	private int start;
	private long totalCount;
	private int totalPageCount;
	private List<T> data;
	private int code = 0;

	public PageBean() {
	}

	public PageBean(long totalCount, List<T> data) {
		this.totalCount = totalCount;
		this.data = data;
	}

	public PageBean(int currentPageNo, int pageSize, int start, long totalCount, int totalPageCount, List<T> data) {
		super();
		this.currentPageNo = currentPageNo;
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalCount;
		if (null == data) {
			totalPageCount = 0;
			data = ListUtils.EMPTY_LIST;
		}
		this.totalPageCount = totalPageCount;
		this.data = data;
	}

	public PageBean(Page page) {
		super();
		this.currentPageNo = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.start = page.getStartRow();
		this.totalCount = page.getTotal();
		List<T> data = page.getResult();
		int totalPageCount = page.getPages();
		if (null == data) {
			totalPageCount = 0;
			data = ListUtils.EMPTY_LIST;
		}
		this.totalPageCount = totalPageCount;
		this.data = data;
	}


}
