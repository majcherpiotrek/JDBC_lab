package com.piotrmajcher.jdbclab.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.piotrmajcher.jdbclab.viewmodels.ReservationViewModel;

public class ReservationsTableData extends AbstractTableModel{
	
	private static final String[] COLUMN_NAMES = {
			"ID",
			"Vehicle number", 
			"Client id", 
			"Name", 
			"Surname", 
			"Start", 
			"End", 
			"Cost"
			};
	
	private List<ReservationViewModel> reservationsList;
	
	public ReservationsTableData() {
		reservationsList = new LinkedList<>();
	}
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return reservationsList.size();
	}

	@Override
	public Object getValueAt(int rows, int columns) {
		ReservationViewModel reservation = reservationsList.get(rows);
		
		switch (columns) {
			case 0: return reservation.getReservationId();
			case 1: return reservation.getCarPlatesNumber();
			case 2: return reservation.getClientId();
			case 3: return reservation.getClientName();
			case 4: return reservation.getClientSurname();
			case 5: return reservation.getStartDate().toString();
			case 6: return reservation.getEndDate().toString();
			case 7: return reservation.getReservationCost();
			default: return null;
		}
	}
	
	@Override
	public String getColumnName(int col) {
		String result = null;
		if (col >= 0 && col < COLUMN_NAMES.length) {
			result = COLUMN_NAMES[col];
		}
		return result;
	}
	
	public void addReservation(ReservationViewModel reservation) {
		if (reservation != null) {
			reservationsList.add(reservation);
			fireTableDataChanged();
		}
	}
	
	public void addReservations(List<ReservationViewModel> reservations) {
		if (reservations != null) {
			reservationsList = reservations;
			fireTableDataChanged();
		}
	}
	
	public List<ReservationViewModel> getReservationsList() {
		return reservationsList;
	}
}
