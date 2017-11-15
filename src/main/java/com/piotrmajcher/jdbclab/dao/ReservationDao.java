package com.piotrmajcher.jdbclab.dao;

import java.util.List;

import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.entities.Reservation;

public interface ReservationDao {
	
	List<Reservation> findAll() throws ReservationDaoException;
	List<Reservation> findAllConfirmed() throws ReservationDaoException;
	List<Reservation> findAllNotExpiredNotConfirmed() throws ReservationDaoException;
	void insertReservation(Reservation reservation) throws ReservationDaoException;
	void deleteReservation(Long reservationId) throws ReservationDaoException;
	void confirmReservation(Long reservationId) throws ReservationDaoException;
}
