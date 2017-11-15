package com.piotrmajcher.jdbclab.dao;

import java.sql.SQLException;
import java.util.List;

import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.viewmodels.CarViewModel;

public interface CarDao {

	List<Car> findAll() throws CarDaoException;
	List<CarViewModel> findAllCarViewModels() throws CarDaoException;
	Car findByPlatesNumber(String platesNumber) throws CarDaoException;
}
