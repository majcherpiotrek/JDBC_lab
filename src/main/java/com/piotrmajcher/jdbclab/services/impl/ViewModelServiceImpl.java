package com.piotrmajcher.jdbclab.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.piotrmajcher.jdbclab.dao.CarDao;
import com.piotrmajcher.jdbclab.dao.ClientDao;
import com.piotrmajcher.jdbclab.dao.ReservationDao;
import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.dao.impl.CarDaoImpl;
import com.piotrmajcher.jdbclab.dao.impl.ClientDaoImpl;
import com.piotrmajcher.jdbclab.dao.impl.ReservationDaoImpl;
import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.entities.Client;
import com.piotrmajcher.jdbclab.entities.Reservation;
import com.piotrmajcher.jdbclab.services.ViewModelService;
import com.piotrmajcher.jdbclab.services.exceptions.ReservationVerificationException;
import com.piotrmajcher.jdbclab.viewmodels.CarViewModel;
import com.piotrmajcher.jdbclab.viewmodels.ReservationViewModel;

public class ViewModelServiceImpl implements ViewModelService {

	private static ViewModelService instance = new ViewModelServiceImpl();
	
	private static final int DAY_IN_MILIS = 1000 * 60 * 60 * 24;
	private CarDao carDao;
	private ReservationDao reservationDao;
	private ClientDao clientDao;
	
	private ViewModelServiceImpl() {
		carDao = CarDaoImpl.getInstance();
		reservationDao = ReservationDaoImpl.getInstance();
		clientDao = ClientDaoImpl.getInstance();
	}
	
	public static ViewModelService getInstance() {
		return instance;
	}
	
	@Override
	public List<CarViewModel> getAllCarViewModels() throws ReservationDaoException, CarDaoException {
		List<CarViewModel> carViewModelList = carDao.findAllCarViewModels();
		List<Reservation> confirmedAndStartedReservations = reservationDao.findAllConfirmed();
		List<Reservation> notConfirmedNorExpiredReservations = reservationDao.findAllNotExpiredNotConfirmed();
		
		for (CarViewModel carViewModel : carViewModelList) {
			confirmedAndStartedReservations = confirmedAndStartedReservations.stream()
					.filter(r -> r.getCar().getPlatesNumber().equals(carViewModel.getPlatesNumber()))
					.collect(Collectors.toList());
			// If the car is on that list
			if (!confirmedAndStartedReservations.isEmpty()) {
				carViewModel.setAvailable(false);
			} else {
				notConfirmedNorExpiredReservations = notConfirmedNorExpiredReservations.stream()
						.filter(r -> r.getCar().getPlatesNumber().equals(carViewModel.getPlatesNumber()))
						.collect(Collectors.toList());
				if (!notConfirmedNorExpiredReservations.isEmpty()) {
					carViewModel.setAvailable(false);
				} else {
					carViewModel.setAvailable(true);
				}
			}
		}
		
		return carViewModelList;
	}

	@Override
	public List<ReservationViewModel> getAllReservationViewModels() throws ReservationDaoException {
		List<Reservation> allNotconfirmedNorExpiredReservations = reservationDao.findAllNotExpiredNotConfirmed();
		List<ReservationViewModel> reservationViewModelList = new LinkedList<>();
		
		for (Reservation reservation : allNotconfirmedNorExpiredReservations) {
			ReservationViewModel reservationViewModel = convertReservationToReservationViewModel(reservation);
			
			reservationViewModelList.add(reservationViewModel);
		}
		
		return reservationViewModelList;
	}

	private Double calculateReservationCost(Reservation reservation) {
		Long reservationDurationMilis = reservation.getEndDate().getTime() - reservation.getStartDate().getTime();
		Double reservationDurationDays = (double) reservationDurationMilis / DAY_IN_MILIS;
		return reservation.getCar().getCarModel().getPricePerDay() * reservationDurationDays;
	}

	@Override
	public List<Client> getAllClients() throws ClientDaoException {
		return clientDao.findAll();
	}

	@Override
	public List<Car> getAllCars() throws CarDaoException {
		return carDao.findAll();
	}

	@Override
	public void addReservation(Reservation reservation) throws ReservationVerificationException, ReservationDaoException {
		verifyReservation(reservation);
		reservationDao.insertReservation(reservation);
	}

	@Override
	public List<ReservationViewModel> getAllRentedCarsViewModels() throws ReservationDaoException {
		List<Reservation> allConfirmedReservations = reservationDao.findAllConfirmed();
		List<ReservationViewModel> reservationViewModelList = new LinkedList<>();
		
		for (Reservation reservation : allConfirmedReservations) {
			ReservationViewModel reservationViewModel = convertReservationToReservationViewModel(reservation);
			reservationViewModelList.add(reservationViewModel);
		}
		
		return reservationViewModelList;
	}
	
	private void verifyReservation(Reservation reservation) throws ReservationVerificationException{
		try {
			List<Reservation> allReservations = reservationDao.findAll();
			for (Reservation existingreservation : allReservations) {
				if (reservation.getCar().getPlatesNumber().equals(existingreservation.getCar().getPlatesNumber())) {
					if (doDatesOverlap(reservation, existingreservation)) {
						throw new ReservationVerificationException("New reservation dates overlap with existing reservation with id :" + existingreservation.getId());
					}
				}
			}
		} catch (ReservationDaoException e) {
			throw new ReservationVerificationException("Reservation verification failed!" + e.getMessage());
		}
	}
	
	private boolean doDatesOverlap(Reservation reservation, Reservation existingreservation) {
		boolean overlap = true;
		if (reservation.getStartDate().before(existingreservation.getStartDate()) 
				&& reservation.getEndDate().before(existingreservation.getStartDate())) {
			overlap = false;;
		}
		
		if (reservation.getStartDate().after(existingreservation.getEndDate())) {
			overlap = false;
		}
		
		return overlap;
	}
	
	private ReservationViewModel convertReservationToReservationViewModel(Reservation reservation) {
		ReservationViewModel reservationViewModel = new ReservationViewModel();
		reservationViewModel.setReservationId(reservation.getId());
		reservationViewModel.setCarPlatesNumber(reservation.getCar().getPlatesNumber());
		reservationViewModel.setClientId(reservation.getClient().getId());
		reservationViewModel.setClientName(reservation.getClient().getName());
		reservationViewModel.setClientSurname(reservation.getClient().getSurname());
		reservationViewModel.setStartDate(reservation.getStartDate());
		reservationViewModel.setEndDate(reservation.getEndDate());
		reservationViewModel.setReservationCost(calculateReservationCost(reservation));
		return reservationViewModel;
	}

	@Override
	public void cancelReservation(Long reservationId) throws ReservationDaoException {
		reservationDao.deleteReservation(reservationId);	
	}

	@Override
	public void confirmReservation(Long reservationId) throws ReservationDaoException {
		reservationDao.confirmReservation(reservationId);
	}
}
