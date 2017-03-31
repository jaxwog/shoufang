package com.example.javabean;

import java.util.List;

public class RentHouseBean {
	private Page page;
	private List<RentHouse> list;
	public RentHouseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RentHouseBean(Page page, List<RentHouse> list) {
		super();
		this.page = page;
		this.list = list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<RentHouse> getList() {
		return list;
	}
	public void setList(List<RentHouse> list) {
		this.list = list;
	}
	
}
