package com.piotrmajcher.jdbclab.viewmodels;

public class CarViewModel {

	private String platesNumber;
	private String brand;
	private String model;
	private Integer pricePerDay;
	private boolean available;
	
	public String getPlatesNumber() {
		return platesNumber;
	}
	public void setPlatesNumber(String platesNumber) {
		this.platesNumber = platesNumber;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(Integer pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
}
