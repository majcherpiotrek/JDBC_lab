package com.piotrmajcher.jdbclab.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.piotrmajcher.jdbclab.dao.ClientDao;
import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.dao.impl.ClientDaoImpl;
import com.piotrmajcher.jdbclab.entities.Reservation;
import com.piotrmajcher.jdbclab.services.ViewModelService;
import com.piotrmajcher.jdbclab.services.exceptions.ReservationVerificationException;
import com.piotrmajcher.jdbclab.services.impl.ViewModelServiceImpl;
import com.piotrmajcher.jdbclab.viewmodels.ReservationViewModel;

import sun.security.provider.VerificationProvider;

public class AppPanelFactory {

	private static Logger logger = Logger.getLogger(AppPanelFactory.class);
	private static ViewModelService viewModelService = ViewModelServiceImpl.getInstance();
	private static CarsTableData carsTableData;
	private static ReservationsTableData reservationsTableData;
	private static ReservationsTableData rentedCarsTableData;
	
	public static AppPanel createAppPanel(Connection dbConnection) throws ClientDaoException, ReservationDaoException, CarDaoException {
		AppPanel appPanel = new AppPanel();
		appPanel.setLayout(new BoxLayout(appPanel, BoxLayout.LINE_AXIS));
		
		JPanel clientsTablePanel = createClientsTablePanel();
		JPanel reservationsTablePanel = createReservationsTablePanel();
		JPanel carsTablePanel = createCarsTablePanel();
		
		appPanel.add(clientsTablePanel);
		appPanel.add(reservationsTablePanel);
		appPanel.add(carsTablePanel);
		return appPanel;
	}

	private static JPanel createCarsTablePanel() throws ReservationDaoException, CarDaoException {
		JPanel carsTablePanel = new JPanel();
		carsTablePanel.setLayout(new BoxLayout(carsTablePanel, BoxLayout.PAGE_AXIS));
		
		JLabel carsTableLabel = new JLabel("Cars");
		
		
		carsTableData = new CarsTableData();
		carsTableData.addCarViewModels(viewModelService.getAllCarViewModels());
		JTable carsTable = new JTable(carsTableData);
		JScrollPane carsTableScrollPane = new JScrollPane(carsTable);
		
		carsTablePanel.add(carsTableLabel);
		carsTablePanel.add(carsTableScrollPane);
		return carsTablePanel;
	}

	private static JPanel createReservationsTablePanel() throws ReservationDaoException {
		// Reservations table
		JPanel reservationsTablePanel = new JPanel();
		reservationsTablePanel.setLayout(new BoxLayout(reservationsTablePanel, BoxLayout.PAGE_AXIS));
		
		JLabel reservationsTableLabel = new JLabel("Reservations");
		
		reservationsTableData = new ReservationsTableData();
		reservationsTableData.addReservations(viewModelService.getAllReservationViewModels());
		JTable reservationsTable = new JTable(reservationsTableData);
		JScrollPane reservationsTableScrollPane = new JScrollPane(reservationsTable);
		
		reservationsTablePanel.add(reservationsTableLabel);
		reservationsTablePanel.add(reservationsTableScrollPane);
		///////////////////////////////////////////////////////////////////////////////////////
		
		// Rented cars table
		JPanel rentedCarsTablePanel = new JPanel();
		rentedCarsTablePanel.setLayout(new BoxLayout(rentedCarsTablePanel, BoxLayout.PAGE_AXIS));
		
		JLabel rentedCarsTableLabel = new JLabel("Rented");
		
		rentedCarsTableData = new ReservationsTableData();
		rentedCarsTableData.addReservations(viewModelService.getAllRentedCarsViewModels());
		JTable rentedCarsTable = new JTable(rentedCarsTableData);
		JScrollPane rentedCarsTableScrollPane = new JScrollPane(rentedCarsTable);
		
		rentedCarsTablePanel.add(rentedCarsTableLabel);
		rentedCarsTablePanel.add(rentedCarsTableScrollPane);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
		
		JButton addReservationButton = new JButton("Add reservation");
		addReservationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Reservation reservation = new AddReservationForm(viewModelService.getAllClients(), viewModelService.getAllCars()).display();
					if (reservation != null) {
						logger.info("Trying to add new reservation: " + reservation);
						viewModelService.addReservation(reservation);
						logger.info("Reservation added!");
						reservationsTableData.addReservations(viewModelService.getAllReservationViewModels());
					}
				} catch (ClientDaoException | CarDaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ReservationVerificationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ReservationDaoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton cancelReservationButton = new JButton("Cancel reservation");
		cancelReservationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<ReservationViewModel> reservations = reservationsTableData.getReservationsList();
				if (!reservations.isEmpty()) {
					ReservationViewModel reservation = new ChooseReservationForm(reservations).display();
					try {
						viewModelService.cancelReservation(reservation.getReservationId());
						reservationsTableData.addReservations(viewModelService.getAllReservationViewModels());
					} catch (ReservationDaoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton confirmReservationButton = new JButton("Confirm reservation");
		confirmReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<ReservationViewModel> reservations = reservationsTableData.getReservationsList();
				
				if (!reservations.isEmpty()) {
					ReservationViewModel reservation = new ChooseReservationForm(reservations).display();
					try {
						viewModelService.confirmReservation(reservation.getReservationId());
						reservationsTableData.addReservations(viewModelService.getAllReservationViewModels());
						rentedCarsTableData.addReservations(viewModelService.getAllRentedCarsViewModels());
						carsTableData.addCarViewModels(viewModelService.getAllCarViewModels());
					} catch (ReservationDaoException | CarDaoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton endReservationButton = new JButton("End reservation");
		endReservationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<ReservationViewModel> reservations = rentedCarsTableData.getReservationsList();
				
				if (!reservations.isEmpty()) {
					ReservationViewModel reservation = new ChooseReservationForm(reservations).display();
					try {
						viewModelService.cancelReservation(reservation.getReservationId());
						reservationsTableData.addReservations(viewModelService.getAllReservationViewModels());
						rentedCarsTableData.addReservations(viewModelService.getAllRentedCarsViewModels());
						carsTableData.addCarViewModels(viewModelService.getAllCarViewModels());
					} catch (ReservationDaoException | CarDaoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		controlPanel.add(addReservationButton);
		controlPanel.add(cancelReservationButton);
		controlPanel.add(confirmReservationButton);
		reservationsTablePanel.add(controlPanel);
		reservationsTablePanel.add(rentedCarsTablePanel);
		reservationsTablePanel.add(endReservationButton);
		return reservationsTablePanel;
	}

	private static JPanel createClientsTablePanel() throws ClientDaoException {
		JPanel clientsTablePanel = new JPanel();
		clientsTablePanel.setLayout(new BoxLayout(clientsTablePanel, BoxLayout.PAGE_AXIS));
		
		JLabel clientsTableLabel = new JLabel("CLIENTS");
		
		ClientDao clientDao = ClientDaoImpl.getInstance();
		ClientsTableData clientsTableData = new ClientsTableData();
		clientsTableData.addClients(clientDao.findAll());
		JTable clientsTable = new JTable(clientsTableData);
		JScrollPane clientsTableScrollPane = new JScrollPane(clientsTable);
		
		clientsTablePanel.add(clientsTableLabel);
		clientsTablePanel.add(clientsTableScrollPane);
		return clientsTablePanel;
	}
}
