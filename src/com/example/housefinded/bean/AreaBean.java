package com.example.housefinded.bean;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;

public class AreaBean implements Serializable{

	private String status;
	private String 	msg;
	private String exmsg;
	private List<CityArea> data;
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
	public List<CityArea> getData() {
		return data;
	}
	public void setData(List<CityArea> data) {
		this.data = data;
	}

}
