package com.jinalim.aop.message;

public class Criteria {
	private int page;
	private int perPageNum;

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		} else {
			this.page = page;
		}
	}

	public void setPerPageNum(int perPageNum) {
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 1;
			return;
		} else {
			this.perPageNum = perPageNum;
		}
	}

	public int getPage() {
		return page;
	}

	public int getPageStart() {
		return (this.page - 1) * perPageNum + 1;
	}
	
	public int getPageEnd() {
		return this.perPageNum * this.page;
	}

	public int getPerPageNum() {
		return this.perPageNum;
	}
	

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
}
