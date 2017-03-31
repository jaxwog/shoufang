package com.example.javabean;
import java.io.Serializable;
/*
 * 用户详情字段
 * 
 * */
public class UserBean implements Serializable{
	private int id;// 用户的ID
	private String name;// 用户名
	private String password;// 密码
	private String showName;// 昵称
	private String gender;// 性别
	private int age;// 年龄
	private String telephone;// 电话
	private String email;// 邮箱
	private String description;// 签名
	private String headImg;// 头像URl
	private int score;//积分
	private String address;// 地址
	private double lng;// 经度
	private double lat;// 纬度
	private String yzm;// 验证码
	private String roleId;//角色
	private String recommendedCode;//推荐人id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getYzm() {
		return yzm;
	}
	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRecommendedCode() {
		return recommendedCode;
	}
	public void setRecommendedCode(String recommendedCode) {
		this.recommendedCode = recommendedCode;
	}
	
}
