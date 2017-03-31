package com.example.javabean;
/*
 * @param 帮你卖房
 * /
 */
public class SellHousebean {
	//{"id":"130","user_id":"122","create_date":"2015-10-30 10:10:16","building_name":"明城家园","house_type":"2室4厅6卫","house_area":"500","house_floor":"5/8层","house_decorate":"东北","house_price":"500","reference_price":null,"house_description":"你们好","house_personal":"15038219245","telephone":"15038219245","tel_time":"9:00-22:00","status":null
	private String   id;//主键id
	private String   user_id;//用户id
	private String   create_date ;//创建日期
	private String   building_name  ;//楼盘名称
	private String   house_type ;//户型
	private String   house_area ;//面积	 
	private String   house_floor ;//楼层
	private String   house_decorate ;//朝向
	private String   house_price ;//价格
	private String   reference_price ;//参考价格
	private String   house_description ;//房源描述
	private String   house_personal;//业主姓名
	private String   telephone ;//手机号码
	private String   tel_time ;//接电时间
	private String   status  ;//发布状态
	private String source;
	public SellHousebean(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}
	public String getHouse_type() {
		return house_type;
	}
	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}
	public String getHouse_area() {
		return house_area;
	}
	public void setHouse_area(String house_area) {
		this.house_area = house_area;
	}
	public String getHouse_floor() {
		return house_floor;
	}
	public void setHouse_floor(String house_floor) {
		this.house_floor = house_floor;
	}
	public String getHouse_decorate() {
		return house_decorate;
	}
	public void setHouse_decorate(String house_decorate) {
		this.house_decorate = house_decorate;
	}
	public String getHouse_price() {
		return house_price;
	}
	public void setHouse_price(String house_price) {
		this.house_price = house_price;
	}
	public String getReference_price() {
		return reference_price;
	}
	public void setReference_price(String reference_price) {
		this.reference_price = reference_price;
	}
	public String getHouse_description() {
		return house_description;
	}
	public void setHouse_description(String house_description) {
		this.house_description = house_description;
	}
	public String getHouse_personal() {
		return house_personal;
	}
	public void setHouse_personal(String house_personal) {
		this.house_personal = house_personal;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTel_time() {
		return tel_time;
	}
	public void setTel_time(String tel_time) {
		this.tel_time = tel_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	

}
