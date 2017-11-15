package com.piotrmajcher.jdbclab.dao;

import java.sql.SQLException;
import java.util.List;

import com.piotrmajcher.jdbclab.entities.CarModel;

public interface CarModelDao {

	List<CarModel> findAll();
	List<CarModel> findAllSortByPricePerDayAscending();
	List<CarModel> findAllSortByPricePerDayDescending();
	CarModel findById(Long id) throws SQLException;
}
