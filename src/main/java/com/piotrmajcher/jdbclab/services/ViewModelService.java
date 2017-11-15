package com.piotrmajcher.jdbclab.services;

import java.util.List;

import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.entities.Client;
import com.piotrmajcher.jdbclab.entities.Reservation;
import com.piotrmajcher.jdbclab.services.exceptions.ReservationVerificationException;
import com.piotrmajcher.jdbclab.viewmodels.CarViewModel;
import com.piotrmajcher.jdbclab.viewmodels.ReservationViewModel;

public interface ViewModelService {

	List<CarViewModel> getAllCarViewModels() throws ReservationDaoException, CarDaoException;
	List<ReservationViewModel> getAllReservationViewModels() throws ReservationDaoException;
	List<ReservationViewModel> getAllRentedCarsViewModels() throws ReservationDaoException;
	List<Client> getAllClients() throws ClientDaoException;
	List<Car> getAllCars() throws CarDaoException;
	void addReservation(Reservation reservation) throws ReservationVerificationException, ReservationDaoException;
	void cancelReservation(Long reservationId) throws ReservationDaoException;
	void confirmReservation(Long reservationId) throws ReservationDaoException;
}
