package com.piotrmajcher.jdbclab.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.piotrmajcher.jdbclab.App;
import com.piotrmajcher.jdbclab.dao.ClientDao;
import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.entities.Client;

public class ClientDaoImpl implements ClientDao{
	
	private static ClientDao instance = new ClientDaoImpl();
	private Connection dbConnection;
	
	private static final String FIND_ALL = "SELECT * FROM CLIENTS";
	private static final String FIND_BY_ID = "SELECT * FROM CLIENTS WHERE id=";
	
	public ClientDaoImpl() {
		dbConnection = App.dbConnection;
	}
	
	public static ClientDao getInstance() {
		return instance;
	}
	
	@Override
	public List<Client> findAll() throws ClientDaoException {
		List<Client> clientsList = new LinkedList<>();
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet rSet = statement.executeQuery(FIND_ALL);
			while (rSet.next()) {
				Client client = new Client();
				client.setId(rSet.getLong(1));
				client.setName(rSet.getString(2));
				client.setSurname(rSet.getString(3));
				clientsList.add(client);
			}
		} catch (SQLException e) {
			throw new ClientDaoException("Could not execute findAll() method for CLIENTS table! Exception: " + e.getMessage());
		}
		return clientsList;
	}

	@Override
	public Client findById(Long id) throws SQLException {
		Statement statement = dbConnection.createStatement();
		ResultSet rSet = statement.executeQuery(FIND_BY_ID + id + ";");
		Client client = new Client();
		while (rSet.next()) {
			client.setId(rSet.getLong("id"));
			client.setName(rSet.getString("name"));
			client.setSurname(rSet.getString("surname"));
		}
		return client;
	}

	@Override
	public void addClient(String name, String surname) {
		// TODO Auto-generated method stub
		
	}

}
