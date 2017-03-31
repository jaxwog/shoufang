package com.example.javabean;

import java.io.Serializable;
import java.util.List;

public class SecondHouseBean implements Serializable{
	private Page page;
	private List< House> list;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<House> getList() {
		return list;
	}
	public void setList(List<House> list) {
		this.list = list;
	}
	
	
}
