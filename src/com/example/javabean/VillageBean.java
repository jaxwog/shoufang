package com.example.javabean;

import java.io.Serializable;

public class VillageBean implements Serializable {
	private String id;// 主键ID

	private String createDate;// 创建时间

	private String villageName;// 小区名称

	private String villageArea;// 区域

	private String houseType;// 建筑类别

	private String propertyType;// 物业类别

	private String buildYear;// 建筑年代

	private String propertyDescription;// 产权描述

	private String propertyCompany;// 物业公司

	private String propertyFee;// 物业费

	private String developer;// 开发商

	private String greenRate;// 绿化率

	private String plotRatio;// 容积率

	private String packingSpace;// 停车位

	private String introduce;// 小区介绍

	private String address;// 地址

	private String villageIndex;// 小区指数

	private String userId;// 用户ID

	private String companyId;// 发布门店ID

	private String imagePath;// 展示图片地址

	private int secondCount;// 小区中二手房的条数

	private int tenementCount;// 小区中租房的条数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageArea() {
		return villageArea;
	}

	public void setVillageArea(String villageArea) {
		this.villageArea = villageArea;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getPropertyDescription() {
		return propertyDescription;
	}

	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}

	public String getPropertyCompany() {
		return propertyCompany;
	}

	public void setPropertyCompany(String propertyCompany) {
		this.propertyCompany = propertyCompany;
	}

	public String getPropertyFee() {
		return propertyFee;
	}

	public void setPropertyFee(String propertyFee) {
		this.propertyFee = propertyFee;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getGreenRate() {
		return greenRate;
	}

	public void setGreenRate(String greenRate) {
		this.greenRate = greenRate;
	}

	public String getPlotRatio() {
		return plotRatio;
	}

	public void setPlotRatio(String plotRatio) {
		this.plotRatio = plotRatio;
	}

	public String getPackingSpace() {
		return packingSpace;
	}

	public void setPackingSpace(String packingSpace) {
		this.packingSpace = packingSpace;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVillageIndex() {
		return villageIndex;
	}

	public void setVillageIndex(String villageIndex) {
		this.villageIndex = villageIndex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getSecondCount() {
		return secondCount;
	}

	public void setSecondCount(int secondCount) {
		this.secondCount = secondCount;
	}

	public int getTenementCount() {
		return tenementCount;
	}

	public void setTenementCount(int tenementCount) {
		this.tenementCount = tenementCount;
	}

}
