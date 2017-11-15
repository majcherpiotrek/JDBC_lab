package com.piotrmajcher.jdbclab.viewmodels;

import java.sql.Timestamp;

public class ReservationViewModel {
	
	private Long reservationId;
	private String carPlatesNumber;
	private Long clientId;
	private String clientName;
	private String clientSurname;
	private Timestamp startDate;
	private Timestamp endDate;
	private Double reservationCost;
	
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public String getCarPlatesNumber() {
		return carPlatesNumber;
	}
	public void setCarPlatesNumber(String carPlatesNumber) {
		this.carPlatesNumber = carPlatesNumber;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientSurname() {
		return clientSurname;
	}
	public void setClientSurname(String clientSurname) {
		this.clientSurname = clientSurname;
	}
	public Timestamp getStartDate() {
		return startDate;
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
	public Double getReservationCost() {
		return reservationCost;
	}
	public void setReservationCost(Double reservationCost) {
		this.reservationCost = reservationCost;
	}
}
