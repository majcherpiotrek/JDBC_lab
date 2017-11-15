package com.piotrmajcher.jdbclab.dao;

import java.sql.SQLException;
import java.util.List;

import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.entities.Client;

public interface ClientDao {

	List<Client> findAll() throws ClientDaoException;
	Client findById(Long id) throws SQLException;
	void addClient(String name, String surname);
}
