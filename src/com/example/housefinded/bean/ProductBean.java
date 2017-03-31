package com.example.housefinded.bean;

public class ProductBean {

	private String near;
	private String toatl;
	private String doormode;
	private String more;
	private boolean flag = false;
	public ProductBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductBean(String near, String toatl, String doormode, String more,
			boolean flag) {
		super();
		this.near = near;
		this.toatl = toatl;
		this.doormode = doormode;
		this.more = more;
		this.flag = flag;
	}
	public String getNear() {
		return near;
	}
	public void setNear(String near) {
		this.near = near;
	}
	public String getToatl() {
		return toatl;
	}
	public void setToatl(String toatl) {
		this.toatl = toatl;
	}
	public String getDoormode() {
		return doormode;
	}
	public void setDoormode(String doormode) {
		this.doormode = doormode;
	}
	public String getMore() {
		return more;
	}
	public void setMore(String more) {
		this.more = more;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
}
