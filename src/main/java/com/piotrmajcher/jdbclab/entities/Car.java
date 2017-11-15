package com.piotrmajcher.jdbclab.entities;

public class Car {

	private String platesNumber;
	private CarModel carModel;
	
	public String getPlatesNumber() {
		return platesNumber;
	}
	public void setPlatesNumber(String platesNumber) {
		this.platesNumber = platesNumber;
	}
	public CarModel getCarModel() {
		return carModel;
	}
	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
	
	@Override
	public String toString() {
		return "Car [platesNumber=" + platesNumber + ", carModel=" + carModel + "]";
	}
}
