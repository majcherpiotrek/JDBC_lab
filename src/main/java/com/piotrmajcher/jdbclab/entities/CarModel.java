package com.piotrmajcher.jdbclab.entities;

public class CarModel {
	
	private Long id;
	private String brand;
	private String model;
	private Integer pricePerDay;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "CarModel [id=" + id + ", brand=" + brand + ", model=" + model + ", pricePerDay=" + pricePerDay + "]";
	}
}
