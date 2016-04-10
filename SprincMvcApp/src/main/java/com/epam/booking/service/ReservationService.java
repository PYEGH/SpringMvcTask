package com.epam.booking.service;

import java.sql.SQLException;
import java.util.List;

import com.epam.booking.model.Reservation;

public interface ReservationService {
	List<Reservation> getAllAvailableReservations();
	
	List<Reservation> getAllAvailableReservations(
			final String selectedDateOfSeance);

	List<Reservation> getReservationByNumber(
			final String selectedReservationNumber);

	void reservateTicket(final String name, final String surname,
			final String number) throws SQLException, InterruptedException;

	void deleteReservation(final String number) throws SQLException,
			InterruptedException;
	
	List<String> getAllAvailableDates();
}
