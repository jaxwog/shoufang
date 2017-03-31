package com.example.javabean;

import java.io.Serializable;

public class InvestProject implements Serializable{
	private String id;
	private String createDate;
	private String name;
	private String annualIncome;
	private String deadline;
	private String amount;
	private String repaymentMethod;
	private String financier;
	private String place;
	private String annualRate;
	private String use;
	private String credit;
	private String planPdate;
	private String actualPdate;
	private String closingDate;
	private String damageControl;
	private String repaymentPlan;
	private String projectList;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRepaymentMethod() {
		return repaymentMethod;
	}
	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}
	public String getFinancier() {
		return financier;
	}
	public void setFinancier(String financier) {
		this.financier = financier;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getPlanPdate() {
		return planPdate;
	}
	public void setPlanPdate(String planPdate) {
		this.planPdate = planPdate;
	}
	public String getActualPdate() {
		return actualPdate;
	}
	public void setActualPdate(String actualPdate) {
		this.actualPdate = actualPdate;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	public String getDamageControl() {
		return damageControl;
	}
	public void setDamageControl(String damageControl) {
		this.damageControl = damageControl;
	}
	public String getRepaymentPlan() {
		return repaymentPlan;
	}
	public void setRepaymentPlan(String repaymentPlan) {
		this.repaymentPlan = repaymentPlan;
	}
	public String getProjectList() {
		return projectList;
	}
	public void setProjectList(String projectList) {
		this.projectList = projectList;
	}

}
