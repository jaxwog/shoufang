package com.example.housefinded.view;

public class PieDataItem {
	private String title;
	private float humidity;

	public PieDataItem() {

	}

	public PieDataItem(String title, float humidity) {
		this.title = title;
		this.humidity = humidity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

}
