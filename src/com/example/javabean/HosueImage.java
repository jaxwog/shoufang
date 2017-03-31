package com.example.javabean;

import java.io.Serializable;

public class HosueImage implements Serializable{
private String id;
private String secondHouse;//
private String source;//图片资源
private String orders;//顺序
private String title;//标题
private String villageId;//小区id
public HosueImage() {
	super();
	// TODO Auto-generated constructor stub
}
public HosueImage(String id, String secondHouse, String source, String orders,
		String title) {
	super();
	this.id = id;
	this.secondHouse = secondHouse;
	this.source = source;
	this.orders = orders;
	this.title = title;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getSecondHouse() {
	return secondHouse;
}
public void setSecondHouse(String secondHouse) {
	this.secondHouse = secondHouse;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getOrders() {
	return orders;
}
public void setOrders(String orders) {
	this.orders = orders;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getVillageId() {
	return villageId;
}
public void setVillageId(String villageId) {
	this.villageId = villageId;
}


}
