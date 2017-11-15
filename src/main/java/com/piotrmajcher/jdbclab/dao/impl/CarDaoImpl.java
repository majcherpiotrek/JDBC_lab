package com.piotrmajcher.jdbclab.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.piotrmajcher.jdbclab.App;
import com.piotrmajcher.jdbclab.dao.CarDao;
import com.piotrmajcher.jdbclab.dao.CarModelDao;
import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.viewmodels.CarViewModel;

public class CarDaoImpl implements CarDao {
	
	private static CarDao instance = new CarDaoImpl();
	
	private static final String FIND_ALL_CARS_JOIN_CAR_MODELS = "SELECT CARS.plates_number, CAR_MODELS.brand, CAR_MODELS.model, CAR_MODELS.price_per_day"
			+ " FROM CARS "
			+ " INNER JOIN CAR_MODELS ON CAR_MODELS.id=CARS.car_model_id;";
	
	private static final String FIND_BY_PLATES_NUMBER = "SELECT * FROM CARS WHERE plates_number=";
	private static final String FIND_ALL = "SELECT * FROM CARS;";
	
	private Connection dbConnection;
	private CarModelDao carModelDao;
	
	private CarDaoImpl() {
		this.dbConnection = App.dbConnection;
		carModelDao = CarModelDaoImpl.getInstance();
	}
	
	public static CarDao getInstance() {
		return instance;
	}
	
	@Override
	public List<Car> findAll() throws CarDaoException {
		List<Car> carsList = new LinkedList<>();
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet rSet = statement.executeQuery(FIND_ALL);
			while (rSet.next()) {
				Car car = new Car();
				car.setPlatesNumber(rSet.getString("plates_number"));
				Long carModelId = rSet.getLong("car_model_id");
				car.setCarModel(carModelDao.findById(carModelId));
				
				carsList.add(car);
			}
		} catch (SQLException e) {
			throw new CarDaoException("Could not execute findAll() for CARS table: " + e.getMessage());
		}
		
		return carsList;
	}

	@Override
	public Car findByPlatesNumber(String platesNumber) throws CarDaoException {
		Car car = null;
		try {
		Statement statement = dbConnection.createStatement();
		
		ResultSet rSet = statement.executeQuery(FIND_BY_PLATES_NUMBER + "'" + platesNumber + "';");
		car = new Car();
		while (rSet.next()) {
			car.setPlatesNumber(rSet.getString("plates_number"));
			car.setCarModel(carModelDao.findById(rSet.getLong("car_model_id")));
		}
		} catch (SQLException e) {
			throw new CarDaoException("Could not execute findByPlatesNumber(String platesNumber) for CARS table: " + e.getMessage());
		}
		return car;
	}

	@Override
	public List<CarViewModel> findAllCarViewModels() throws CarDaoException {
		List<CarViewModel> carViewModelList = new LinkedList<>();
		try {
		Statement statement = dbConnection.createStatement();
		ResultSet rSet = statement.executeQuery(FIND_ALL_CARS_JOIN_CAR_MODELS);
		
		while (rSet.next()) {
			CarViewModel carViewModel = new CarViewModel();
			carViewModel.setPlatesNumber(rSet.getString("plates_number"));
			carViewModel.setBrand(rSet.getString("brand"));
			carViewModel.setModel(rSet.getString("model"));
			carViewModel.setPricePerDay(rSet.getInt("price_per_day"));
			
			carViewModelList.add(carViewModel);
		}
		} catch (SQLException e) {
			throw new CarDaoException("Could not execute findAllCarViewModels() for CARS and CAR_MODELS tables: " + e.getMessage());
		}
		
		return carViewModelList;
	}		
}
