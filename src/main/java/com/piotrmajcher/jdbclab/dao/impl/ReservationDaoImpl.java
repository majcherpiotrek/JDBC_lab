package com.piotrmajcher.jdbclab.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.piotrmajcher.jdbclab.App;
import com.piotrmajcher.jdbclab.dao.CarDao;
import com.piotrmajcher.jdbclab.dao.ClientDao;
import com.piotrmajcher.jdbclab.dao.ReservationDao;
import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.entities.Client;
import com.piotrmajcher.jdbclab.entities.Reservation;

public class ReservationDaoImpl implements ReservationDao {
	
	private static ReservationDao instance = new ReservationDaoImpl();
	
	private static final String FIND_ALL_CONFIRMED = "SELECT * FROM RESERVATIONS WHERE confirmed=TRUE;";
	private static final String FIND_ALL_NOT_EXPIRED_NOT_CONFIRMED = "SELECT * FROM RESERVATIONS WHERE confirmed=FALSE AND expiry_date>";
	private static final String FIND_ALL = "SELECT * FROM RESERVATIONS;";
	private static final String INSERT_RESERVATION = "INSERT INTO RESERVATIONS (car_plates_number, client_id, start_date, expiry_date, end_date, confirmed) VALUES ";
	private static final String DELETE_RESERVATION = "DELETE FROM RESERVATIONS WHERE id=";
	private static final String CONFIRM_RESERVATION = "UPDATE RESERVATIONS SET confirmed=TRUE WHERE id=";
	private Connection dbConnection;
	private CarDao carDao;
	private ClientDao clientDao;
	
	private ReservationDaoImpl() {
		dbConnection = App.dbConnection;
		carDao = CarDaoImpl.getInstance();
		clientDao = ClientDaoImpl.getInstance();
	}
	
	public static ReservationDao getInstance() {
		return instance;
	}

	@Override
	public List<Reservation> findAllConfirmed() throws ReservationDaoException {
		List<Reservation> reservationsList = new LinkedList<>();
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet rSet = statement.executeQuery(FIND_ALL_CONFIRMED);
			reservationsList.addAll(createReservationFromResultSet(rSet));
		} catch (SQLException | CarDaoException e) {
			throw new ReservationDaoException("Could not execute findAllConfirmed() for RESERVATIONS table: " + e.getMessage());
		}
		return reservationsList;
	}

	@Override
	public List<Reservation> findAllNotExpiredNotConfirmed() throws ReservationDaoException {
		List<Reservation> reservationsList = new LinkedList<>();
		try {
		Statement statement = dbConnection.createStatement();
		Date dateNow = new Date();
		Timestamp now = new Timestamp(dateNow.getTime());
		ResultSet rSet = statement.executeQuery(FIND_ALL_NOT_EXPIRED_NOT_CONFIRMED + "'" + now.toString() + "';");
		reservationsList.addAll(createReservationFromResultSet(rSet));
		} catch (SQLException | CarDaoException e) {
			throw new ReservationDaoException("Could not execute findAllNotExpiredNotConfirmed() for RESERVATIONS table: " + e.getMessage());
		}
		return reservationsList;
	}
	
	private List<Reservation> createReservationFromResultSet(ResultSet rSet)
			throws SQLException, CarDaoException {
		List<Reservation> reservationsList = new LinkedList<>();
		while (rSet.next()) {
			Reservation reservation = new Reservation();
			reservation.setId(rSet.getLong("id"));
			reservation.setCar(carDao.findByPlatesNumber(rSet.getString("car_plates_number")));
			reservation.setClient(clientDao.findById(rSet.getLong("client_id")));
			reservation.setStartDate(rSet.getTimestamp("start_date"));
			reservation.setExpiryDate(rSet.getTimestamp("expiry_date"));
			reservation.setEndDate(rSet.getTimestamp("end_date"));
			reservation.setConfirmed(rSet.getBoolean("confirmed"));
			
			reservationsList.add(reservation);
		}
		return reservationsList;
	}

	@Override
	public List<Reservation> findAll() throws ReservationDaoException {
		List<Reservation> reservationsList = new LinkedList<>();
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet rSet = statement.executeQuery(FIND_ALL);
			reservationsList = createReservationFromResultSet(rSet);
		} catch (SQLException | CarDaoException e) {
			throw new ReservationDaoException("Could not execute findAll() for RESERVATIONS table: " + e.getMessage());
		}
		
		return reservationsList;
	}

	@Override
	public void insertReservation(Reservation reservation) throws ReservationDaoException {
		StringBuilder sb = new StringBuilder();
		sb.append(INSERT_RESERVATION);
		sb.append("(");
		sb.append("'" + reservation.getCar().getPlatesNumber() + "'");
		sb.append("," + reservation.getClient().getId().toString());
		sb.append(",'" + reservation.getStartDate().toString() + "'");
		sb.append(",'" + reservation.getExpiryDate().toString() + "'");
		sb.append(",'" + reservation.getEndDate().toString() + "'");
		sb.append("," + (reservation.getConfirmed() ? "TRUE" : "FALSE") + ");");
		
		try {
			Statement statement = dbConnection.createStatement();
			statement.execute(sb.toString());
		} catch (SQLException e) {
			throw new ReservationDaoException("Could not insert new reservation: " + e.getMessage());
		}
	}

	@Override
	public void deleteReservation(Long reservationId) throws ReservationDaoException {
		try {
			Statement statement = dbConnection.createStatement();
			statement.execute(DELETE_RESERVATION + reservationId + ";");
		} catch (SQLException e) {
			throw new ReservationDaoException("Could not delete reservation with id " + reservationId + ": " + e.getMessage());
		}
	}

	@Override
	public void confirmReservation(Long reservationId) throws ReservationDaoException {
		try {
			Statement statement = dbConnection.createStatement();
			statement.execute(CONFIRM_RESERVATION + reservationId + ";");
		} catch (SQLException e) {
			throw new ReservationDaoException("Could not confirm reservation with id " + reservationId + ": " + e.getMessage());
		}
	}
}
