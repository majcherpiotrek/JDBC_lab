package com.piotrmajcher.jdbclab.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.piotrmajcher.jdbclab.entities.Client;

public class ClientsTableData extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = {"id", "Name", "Surname"};
	private List<Client> clientsList;
	
	public ClientsTableData() {
		this.clientsList = new LinkedList<>();
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return clientsList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Client client = clientsList.get(row);
		switch (column) {
		case 0: return client.getId();
		case 1: return client.getName();
		case 2: return client.getSurname();
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
	
	public void addClient(Client client) {
		if (client != null) {
			clientsList.add(client);
		}
	}
	
	public void addClients(List<Client> clients) {
		if (clients != null) {
			clientsList.addAll(clients);
		}
	}
}
