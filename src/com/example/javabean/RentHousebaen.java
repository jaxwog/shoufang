package com.example.javabean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RentHousebaen implements Serializable {

	private String id;// 租房ID

	private String userId;// 用户ID

	private String createDate;// 发布时间

	private String title;// 标题

	private String cellName;// 小区名称

	private String cellAdress;// 小区地址

	private String cellArea;// 所在区域

	private String type;// 租房类型（“1”表示：住宅，“2”表示：别墅，“3”表示：写字楼，“4”表示：商铺）

	private String tenType;// 租赁方式（“0”表示：整租，“1”表示：合租）

	private String jointrentType;// 合租类型

	private String jointrentMode;// 合租方式

	private String houseType;// 户型

	private String houseArea;// 房屋面积

	private String houseLayer;// 地上层数

	private String houseMode;// 建筑形式

	private String rent;// 租金

	private String rentUnit;// 租金单位

	private String isPropertyfee;// 是否包含物业费

	private String propertyfee;// 物业费

	private String officeLevel;// 写字楼级别

	private String officeType;// 写字楼类型

	private String shopsType;// 商铺类型

	private String shopsStatus;// 商铺状态

	private String isAttorn;// 是否转让

	private String isCede;// 是否割让

	private String shopsTarget;// 商铺目标业态

	private boolean bed;// 是否有床

	private boolean broadband;// 是否有宽带

	private boolean tv;// 是否有电视

	private boolean washer;// 是否有洗衣机

	private boolean heating;// 是否有暖气

	private boolean airconditioner;// 是否有空调

	private boolean fridge;// 是否有冰箱

	private boolean waterHeater;// 是否有热水器

	private String housePtss;// 配套设施

	private String houseLabel;// 房源标签

	private String description;// 描述

	private String personalName;// 姓名

	private String telephone;// 联系电话

	private String release;// 发布状态（“2”表示：待发布，“1”表示已发布）

	private String userType;// 用户类型（“0”表示：经纪人，“1”表示：个人）

	// 以下为子表的属性（其中子表的 tenement_id//租房信息ID，为主表的ID）
	private String face;// 朝向
	private String decorate;// 装修
	private String payment_method;// 支付方式
	private String floor;// 楼层
	private String ban;// 楼栋
	private String roomnum;// 房号
	private String house_type;// 户型
	private String sex_limit;// 性别限制
	private String zbpt;// 周边配套
	private String jtlx;// 交通路线
	private String imagePath;// 图片 路径
	private String companyId;// 所属门店

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellAdress() {
		return cellAdress;
	}

	public void setCellAdress(String cellAdress) {
		this.cellAdress = cellAdress;
	}

	public String getCellArea() {
		return cellArea;
	}

	public void setCellArea(String cellArea) {
		this.cellArea = cellArea;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTenType() {
		return tenType;
	}

	public void setTenType(String tenType) {
		this.tenType = tenType;
	}

	public String getJointrentType() {
		return jointrentType;
	}

	public void setJointrentType(String jointrentType) {
		this.jointrentType = jointrentType;
	}

	public String getJointrentMode() {
		return jointrentMode;
	}

	public void setJointrentMode(String jointrentMode) {
		this.jointrentMode = jointrentMode;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseLayer() {
		return houseLayer;
	}

	public void setHouseLayer(String houseLayer) {
		this.houseLayer = houseLayer;
	}

	public String getHouseMode() {
		return houseMode;
	}

	public void setHouseMode(String houseMode) {
		this.houseMode = houseMode;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getRentUnit() {
		return rentUnit;
	}

	public void setRentUnit(String rentUnit) {
		this.rentUnit = rentUnit;
	}

	public String getIsPropertyfee() {
		return isPropertyfee;
	}

	public void setIsPropertyfee(String isPropertyfee) {
		this.isPropertyfee = isPropertyfee;
	}

	public String getPropertyfee() {
		return propertyfee;
	}

	public void setPropertyfee(String propertyfee) {
		this.propertyfee = propertyfee;
	}

	public String getOfficeLevel() {
		return officeLevel;
	}

	public void setOfficeLevel(String officeLevel) {
		this.officeLevel = officeLevel;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getShopsType() {
		return shopsType;
	}

	public void setShopsType(String shopsType) {
		this.shopsType = shopsType;
	}

	public String getShopsStatus() {
		return shopsStatus;
	}

	public void setShopsStatus(String shopsStatus) {
		this.shopsStatus = shopsStatus;
	}

	public String getIsAttorn() {
		return isAttorn;
	}

	public void setIsAttorn(String isAttorn) {
		this.isAttorn = isAttorn;
	}

	public String getIsCede() {
		return isCede;
	}

	public void setIsCede(String isCede) {
		this.isCede = isCede;
	}

	public String getShopsTarget() {
		return shopsTarget;
	}

	public void setShopsTarget(String shopsTarget) {
		this.shopsTarget = shopsTarget;
	}

	public boolean isBed() {
		return bed;
	}

	public void setBed(boolean bed) {
		this.bed = bed;
	}

	public boolean isBroadband() {
		return broadband;
	}

	public void setBroadband(boolean broadband) {
		this.broadband = broadband;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isWasher() {
		return washer;
	}

	public void setWasher(boolean washer) {
		this.washer = washer;
	}

	public boolean isHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public boolean isAirconditioner() {
		return airconditioner;
	}

	public void setAirconditioner(boolean airconditioner) {
		this.airconditioner = airconditioner;
	}

	public boolean isFridge() {
		return fridge;
	}

	public void setFridge(boolean fridge) {
		this.fridge = fridge;
	}

	public boolean isWaterHeater() {
		return waterHeater;
	}

	public void setWaterHeater(boolean waterHeater) {
		this.waterHeater = waterHeater;
	}

	public String getHousePtss() {
		return housePtss;
	}

	public void setHousePtss(String housePtss) {
		this.housePtss = housePtss;
	}

	public String getHouseLabel() {
		return houseLabel;
	}

	public void setHouseLabel(String houseLabel) {
		this.houseLabel = houseLabel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getDecorate() {
		return decorate;
	}

	public void setDecorate(String decorate) {
		this.decorate = decorate;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}

	public String getHouse_type() {
		return house_type;
	}

	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}

	public String getSex_limit() {
		return sex_limit;
	}

	public void setSex_limit(String sex_limit) {
		this.sex_limit = sex_limit;
	}

	public String getZbpt() {
		return zbpt;
	}

	public void setZbpt(String zbpt) {
		this.zbpt = zbpt;
	}

	public String getJtlx() {
		return jtlx;
	}

	public void setJtlx(String jtlx) {
		this.jtlx = jtlx;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
