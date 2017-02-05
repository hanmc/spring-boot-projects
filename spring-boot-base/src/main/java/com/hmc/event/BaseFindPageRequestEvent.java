package com.hmc.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 分页查询请求参数基类<p>
 * 包含了分页查询所需要的一些参数
 * @author hanmc
 *
 */
public class BaseFindPageRequestEvent extends BaseRequestEvent implements IBaseFindPageEvent{
	
	//页码
	private int page;
	
	//每页显示条数
	private int size = 10;
	
	@SuppressWarnings("unused")
	private Pageable pageable;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Pageable getPageable() {
		//第一页对应的page值为0
		return new PageRequest(page-1, size, getSort());
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

}
