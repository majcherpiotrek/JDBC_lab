package com.piotrmajcher.jdbclab.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.piotrmajcher.jdbclab.entities.Client;
import com.piotrmajcher.jdbclab.viewmodels.CarViewModel;

public class CarsTableData extends AbstractTableModel{
	
	private static final String[] COLUMN_NAMES = {"Vehicle number", "Make", "Model", "Price", "Available"};
	private List<CarViewModel> carsList;
	
	public CarsTableData() {
		carsList = new LinkedList<>();
	}
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return carsList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		CarViewModel carViewModel = carsList.get(row);
		switch (column) {
		case 0: return carViewModel.getPlatesNumber();
		case 1: return carViewModel.getBrand();
		case 2: return carViewModel.getModel();
		case 3: return carViewModel.getPricePerDay();
		case 4: return carViewModel.isAvailable();
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
	
	public void addCarViewModel(CarViewModel carViewModel) {
		if (carViewModel != null) {
			carsList.add(carViewModel);
			fireTableDataChanged();
		}
	}
	
	public void addCarViewModels(List<CarViewModel> carViewModels) {
		if (carViewModels != null) {
			carsList = carViewModels;
			fireTableDataChanged();
		}
	}
}
