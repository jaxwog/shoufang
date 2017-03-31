package com.example.javabean;

import java.util.List;

public class PublicHouseBean {
private List<SellHousebean> list;//发布房子的列表
private int page;//后期分页
private int num;//发布房子信息的数量
public PublicHouseBean() {
	super();
}
public List<SellHousebean> getList() {
	return list;
}
public void setList(List<SellHousebean> list) {
	this.list = list;
}
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
}
