package com.piotrmajcher.jdbclab.entities;

import java.sql.Timestamp;

public class Reservation {
	
	private Long id;
	private Car car;
	private Client client;
	private Timestamp startDate;
	private Timestamp expiryDate;
	private Timestamp endDate;
	private Boolean confirmed;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", car=" + car + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", confirmed=" + confirmed + "]";
	}
}
