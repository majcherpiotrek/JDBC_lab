package com.piotrmajcher.jdbclab.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.piotrmajcher.jdbclab.entities.Car;
import com.piotrmajcher.jdbclab.entities.Client;
import com.piotrmajcher.jdbclab.entities.Reservation;

public class AddReservationForm {
	
	private static final Logger logger = Logger.getLogger(AddReservationForm.class);
	private static final long ONE_DAY_IN_MILIS = 1000 * 60 * 60 * 24;
	private List<Client> clients;
	private List<Car> cars;
	private Date startDate = null;
	private Date endDate = null;
	
	public AddReservationForm(List<Client> clients, List<Car> cars) {
		this.clients = clients;
		this.cars = cars;
	}
	
	public Reservation display() {
		List<String> clientsChoices = createClientsChoicesList();
		List<String> carChoices = createCarsChoicesList();
		
		JLabel chooseClientLabel = new JLabel("Choose client");
        JComboBox<String> comboClients = new JComboBox<>(clientsChoices.toArray(new String[0]));
        chooseClientLabel.setLabelFor(comboClients);
        JLabel chooseCarLabel = new JLabel("Choose car");
        JComboBox<String> comboCars = new JComboBox<>(carChoices.toArray(new String[0]));
        chooseCarLabel.setLabelFor(comboCars);
        
        JLabel startDatePickerLabel = new JLabel("Pick start date");
        JXDatePicker startDatePicker = new JXDatePicker();
        startDatePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startDate = startDatePicker.getDate();
			}
		});
        startDatePickerLabel.setLabelFor(startDatePicker);
        
        JLabel endDatePickerLabel = new JLabel("Pick end date");
        JXDatePicker endDatePicker = new JXDatePicker();
        endDatePicker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				endDate = endDatePicker.getDate();	
			}
		});
        endDatePickerLabel.setLabelFor(endDatePicker);   
        
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(chooseClientLabel);
        panel.add(comboClients);
        panel.add(chooseCarLabel);
        panel.add(comboCars);
        panel.add(startDatePickerLabel);
        panel.add(startDatePicker);
        panel.add(endDatePickerLabel);
        panel.add(endDatePicker);
        panel.add(errorLabel);
       
        boolean dataCorrect = false;
        Reservation reservation = null;
        while (!dataCorrect) {
        	int result = JOptionPane.showConfirmDialog(null, panel, "Add new reservation",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if (startDate == null || endDate == null) {
                    	errorLabel.setText("You have to specify the dates!");
                    	continue;
                    }
                    
                    if (endDate.before(startDate) || endDate.equals(startDate)) {
                		errorLabel.setText("End date has to be at least one day after the start date!");
                		continue;
                	}
                    
                    if (comboClients.getSelectedItem() == null) {
                    	errorLabel.setText("You have to select a client!");
                    	continue;
                    }
                    
                    if (comboCars.getSelectedItem() == null) {
                    	errorLabel.setText("You have to select a car!");
                    	continue;
                    }
                    
                    Client pickedClient = clients.stream()
                    		.filter(c -> c.getId().equals(Long.valueOf(comboClients.getSelectedItem().toString())))
                    		.findFirst()
                    		.get();
                    
                    Car pickedCar = cars.stream()
                    		.filter(c -> c.getPlatesNumber().equals(comboCars.getSelectedItem().toString()))
                    		.findFirst()
                    		.get();
                    
                    reservation = new Reservation();
                    reservation.setClient(pickedClient);
                    reservation.setCar(pickedCar);
                    reservation.setStartDate(new Timestamp(startDate.getTime()));
                    reservation.setEndDate(new Timestamp(endDate.getTime()));
                    reservation.setExpiryDate(new Timestamp(startDate.getTime() + ONE_DAY_IN_MILIS));
                    reservation.setConfirmed(false);
                    dataCorrect = true;
                } else {
                    logger.info("Adding reservation cancelled");
                    break;
                }
        }
        
		return reservation;
    }

	private List<String> createCarsChoicesList() {
		List<String> carChoices = new LinkedList<>();
		for (Car car : cars) {
			carChoices.add(car.getPlatesNumber());
		}
		return carChoices;
	}

	private List<String> createClientsChoicesList() {
		List<String> clientsChoices = new LinkedList<>();
		for (Client client : clients) {
			clientsChoices.add(client.getId().toString());
		}
		return clientsChoices;
	}
}
