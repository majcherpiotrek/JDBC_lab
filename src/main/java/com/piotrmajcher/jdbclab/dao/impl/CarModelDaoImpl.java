package com.piotrmajcher.jdbclab.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.piotrmajcher.jdbclab.App;
import com.piotrmajcher.jdbclab.dao.CarModelDao;
import com.piotrmajcher.jdbclab.entities.CarModel;

public class CarModelDaoImpl implements CarModelDao {
	
	private static CarModelDao instance = new CarModelDaoImpl();
	
	private static final String FIND_BY_ID = "SELECT * FROM CAR_MODELS WHERE id=";
	private Connection dbConnection;
	
	private CarModelDaoImpl() {
		dbConnection = App.dbConnection;
	}
	
	public static CarModelDao getInstance() {
		return instance;
	}
	
	@Override
	public List<CarModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CarModel> findAllSortByPricePerDayAscending() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CarModel> findAllSortByPricePerDayDescending() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarModel findById(Long id) throws SQLException {
		Statement statement = dbConnection.createStatement();
		ResultSet rSet = statement.executeQuery(FIND_BY_ID + id + ";");
		CarModel carModel = new CarModel();
		while (rSet.next()) {
			carModel.setId(rSet.getLong("id"));
			carModel.setBrand(rSet.getString("brand"));
			carModel.setModel(rSet.getString("model"));
			carModel.setPricePerDay(rSet.getInt("price_per_day"));
		}
		return carModel;
	}

}
