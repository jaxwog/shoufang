package com.example.javabean;

import java.io.Serializable;
import java.util.List;

public class HouseImageBean implements Serializable{
	
	private List<HosueImage> data;

	public List<HosueImage> getData() {
		return data;
	}

	public void setData(List<HosueImage> data) {
		this.data = data;
	}

}
