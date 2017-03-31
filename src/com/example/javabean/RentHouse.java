package com.example.javabean;

import java.io.Serializable;

public class RentHouse implements Serializable {
	private String villageId;//小区id
	private String id;//房屋id
	private String userId;//用户id
	private String createDate;
	private String releaseDate;
	private String title;
	private String cellName;
	private String cellAdress;
	private String cellArea;
	private String type;
	private String tenType;
	private String jointrentType;
	private String jointrentMode;
	private String houseType;
	private String houseArea;
	private String houseLayer;
	private String houseMode;
	private String rent;
	private String rentUnit;
	private String isPropertyfee;
	private String propertyfee;

	private String officeLevel;
	private String officeType;
	private String shopsType;
	private String shopsStatus;
	private String isAttorn;
	private String isCede;
	private String shopsTarget;
	private String bed;
	private String broadband;
	private String tv;
	private String washer;
	private String heating;
	private String airconditioner;
	private String fridge;
	private String waterHeater;
	private String housePtss;
	private String houseLabel;
	private String description;
	private String personalName;
	private String telephone;
	private String release;
	private String userType;
	private String face;
	private String decorate;
	private String payment_method;
	private String floor;
	private String ban;
	private String roomnum;
	private String house_type;
	private String sex_limit;
	private String imagePath;
	private String zbpt;// 周边配套
	private String jtlx;// 交通路线
	private String companyId;// 所属门店
	private String issee;// 1代表查看过该房子 0代表没有
	private String iscol;// 1代表收藏该房子 0没有
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getIssee() {
		return issee;
	}

	public void setIssee(String issee) {
		this.issee = issee;
	}

	public String getIscol() {
		return iscol;
	}

	public void setIscol(String iscol) {
		this.iscol = iscol;
	}

	public RentHouse() {
		super();
	}

	public RentHouse(String id, String userId, String createDate,
			String releaseDate, String title, String cellName,
			String cellAdress, String cellArea, String type, String tenType,
			String jointrentType, String jointrentMode, String houseType,
			String houseArea, String houseLayer, String houseMode, String rent,
			String rentUnit, String isPropertyfee, String propertyfee,
			String officeLevel, String officeType, String shopsType,
			String shopsStatus, String isAttorn, String isCede,
			String shopsTarget, String bed, String broadband, String tv,
			String washer, String heating, String airconditioner,
			String fridge, String waterHeater, String housePtss,
			String houseLabel, String description, String personalName,
			String telephone, String release, String userType, String face,
			String decorate, String payment_method, String floor, String ban,
			String roomnum, String house_type, String sex_limit,
			String imagePath) {
		super();
		this.id = id;
		this.userId = userId;
		this.createDate = createDate;
		this.releaseDate = releaseDate;
		this.title = title;
		this.cellName = cellName;
		this.cellAdress = cellAdress;
		this.cellArea = cellArea;
		this.type = type;
		this.tenType = tenType;
		this.jointrentType = jointrentType;
		this.jointrentMode = jointrentMode;
		this.houseType = houseType;
		this.houseArea = houseArea;
		this.houseLayer = houseLayer;
		this.houseMode = houseMode;
		this.rent = rent;
		this.rentUnit = rentUnit;
		this.isPropertyfee = isPropertyfee;
		this.propertyfee = propertyfee;
		this.officeLevel = officeLevel;
		this.officeType = officeType;
		this.shopsType = shopsType;
		this.shopsStatus = shopsStatus;
		this.isAttorn = isAttorn;
		this.isCede = isCede;
		this.shopsTarget = shopsTarget;
		this.bed = bed;
		this.broadband = broadband;
		this.tv = tv;
		this.washer = washer;
		this.heating = heating;
		this.airconditioner = airconditioner;
		this.fridge = fridge;
		this.waterHeater = waterHeater;
		this.housePtss = housePtss;
		this.houseLabel = houseLabel;
		this.description = description;
		this.personalName = personalName;
		this.telephone = telephone;
		this.release = release;
		this.userType = userType;
		this.face = face;
		this.decorate = decorate;
		this.payment_method = payment_method;
		this.floor = floor;
		this.ban = ban;
		this.roomnum = roomnum;
		this.house_type = house_type;
		this.sex_limit = sex_limit;
		this.imagePath = imagePath;
	}

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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getBroadband() {
		return broadband;
	}

	public void setBroadband(String broadband) {
		this.broadband = broadband;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public String getWasher() {
		return washer;
	}

	public void setWasher(String washer) {
		this.washer = washer;
	}

	public String getHeating() {
		return heating;
	}

	public void setHeating(String heating) {
		this.heating = heating;
	}

	public String getAirconditioner() {
		return airconditioner;
	}

	public void setAirconditioner(String airconditioner) {
		this.airconditioner = airconditioner;
	}

	public String getFridge() {
		return fridge;
	}

	public void setFridge(String fridge) {
		this.fridge = fridge;
	}

	public String getWaterHeater() {
		return waterHeater;
	}

	public void setWaterHeater(String waterHeater) {
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVillageId() {
		return villageId;
	}

	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}

}