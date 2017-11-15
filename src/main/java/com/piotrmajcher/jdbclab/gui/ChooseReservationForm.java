package com.piotrmajcher.jdbclab.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.piotrmajcher.jdbclab.viewmodels.ReservationViewModel;

public class ChooseReservationForm {

	private static final Logger logger = Logger.getLogger(ChooseReservationForm.class);
	private List<ReservationViewModel> reservations;
	
	public ChooseReservationForm(List<ReservationViewModel> reservations) {
		this.reservations = reservations;
	}
	
	public ReservationViewModel display() {
		List<String> reservationsChoices = createReservationsChoicesList();
		
		JLabel chooseReservationLabel = new JLabel("Choose reservation to cancel");
        JComboBox<String> comboReservations = new JComboBox<>(reservationsChoices.toArray(new String[0]));
        chooseReservationLabel.setLabelFor(comboReservations);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(chooseReservationLabel);
        panel.add(comboReservations);
        
        ReservationViewModel reservation = null;
        	int result = JOptionPane.showConfirmDialog(null, panel, "Choose reservation",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                	reservation = reservations.stream()
                			.filter(r -> r.getReservationId().equals(Long.valueOf(comboReservations.getSelectedItem().toString())))
                			.findFirst()
                			.get();
                } else {
                    logger.info("Cancelled");
                    
                }
        
        
		return reservation;
    }

	private List<String> createReservationsChoicesList() {
		List<String> reservationsChoices = new LinkedList<>();
		for (ReservationViewModel r : reservations) {
			reservationsChoices.add(r.getReservationId().toString());
		}
		return reservationsChoices;
	}
}
