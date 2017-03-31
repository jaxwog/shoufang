package com.example.javabean;

import java.io.Serializable;

public class CollectBean implements Serializable{
  private String id;//收藏id
  private String houseId;//房屋id
  private String houseType;//房屋类型（“1”表示：“二手房”，“2”表示：“租房”，“3”表示：“新房”）
  private String title;//标题
  private String imagePath;//展示图片地址
  private String tenType;//租赁方式（“0”表示：整租，“1”表示：合租）
  private String address;//地址
  private String releaseDate;//发布时间
  private String price;//价格
  private String type;//户型
  private String houseArea;//建筑面积
public String getId() {
	return id;
}
public void setId(String id) {
	id = id;
}
public String getHouseId() {
	return houseId;
}
public void setHouseId(String houseId) {
	this.houseId = houseId;
}
public String getHouseType() {
	return houseType;
}
public void setHouseType(String houseType) {
	this.houseType = houseType;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getImagePath() {
	return imagePath;
}
public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
}
public String getTenType() {
	return tenType;
}
public void setTenType(String tenType) {
	this.tenType = tenType;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getReleaseDate() {
	return releaseDate;
}
public void setReleaseDate(String releaseDate) {
	this.releaseDate = releaseDate;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getHouseArea() {
	return houseArea;
}
public void setHouseArea(String houseArea) {
	this.houseArea = houseArea;
}
  
  
  
}
