package com.example.housefinded.bean;

import java.io.Serializable;

import com.example.javabean.House;

public class HouseBean implements Serializable {

	private String status;
	private String msg;
	private String exmsg;
	private House data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getExmsg() {
		return exmsg;
	}

	public void setExmsg(String exmsg) {
		this.exmsg = exmsg;
	}

	public House getData() {
		return data;
	}

	public void setData(House data) {
		this.data = data;
	}

}
